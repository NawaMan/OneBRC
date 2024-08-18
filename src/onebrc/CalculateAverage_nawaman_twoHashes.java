package onebrc;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.nio.channels.FileChannel.MapMode.READ_ONLY;
import static java.nio.file.StandardOpenOption.READ;
import static java.util.concurrent.Executors.newFixedThreadPool;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

// Use two hash functions and skip array value comparison when "equals" is called.

/**
 * One-Billion Row Challenge solution by NawaMan.
 * 
 * Key Points:
 * <ol>
 * <li>Mapped file to memory for ask IO access.</li>
 * <li>Parallel process of data in chunks.</li>
 * <li>Chunk separation with minimum overlaps.</li>
 * <li>Store name in a fix length bytes so we don't need to find out the length before create the array.</li>
 * <li>Reuse name array if one already exists in the collection.</li>
 * <li>Pre-calculate hashCode when parsing (finding out start and end).</li>
 * <li>Use two hashes to avoid calling comparing the full bytes.</li>
 * </ol>
 */
public class CalculateAverage_nawaman_twoHashes {
    
    /**
     * Station name -- the name of the station.
     **/
    static class StationName implements Comparable<StationName> {
        final byte[] nameArray;
        final int    nameLength;
        final int    hashCode1;
        final int    hashCode2;
        
        private volatile String toString = null;
        
        StationName(byte[] nameArray, int nameLength, int nameHash1, int nameHash2) {
            this.nameArray  = nameArray;
            this.nameLength = nameLength;
            this.hashCode1  = nameHash1;
            this.hashCode2  = nameHash2;
        }
        
        @Override
        public int hashCode() {
            return hashCode1 ^ hashCode2;
        }
        
        @Override
        public boolean equals(Object obj) {
            // Not needed as we are the only one using this class.
//            if (this == obj)
//                return true;
//            if (!(obj instanceof StationName))
//                return false;
            
            var other = (StationName) obj;
            if (nameLength != other.nameLength)
                return false;
//            if (hashCode() != other.hashCode())
//                return false;
            if (hashCode1 != other.hashCode1)
                return false;
            if (hashCode2 != other.hashCode2)
                return false;
            
            return Arrays.equals(nameArray, 0, nameLength, other.nameArray, 0, nameLength);
//            return true;
        }
        
        @Override
        public String toString() {
            if (toString == null) {
                synchronized (this) {
                    if (toString == null) {
                        toString = new String(nameArray, 0, nameLength);
                    }
                }
            }
            return toString;
        }
        
        @Override
        public int compareTo(StationName o) {
            // Avoid handling sorting of UTF-8 characters so convert it to string.
            return this.toString().compareTo(o.toString());
        }
    }
    
    /** Statistic data for a station. */
    static class Station {
        StationName stationName;
        long        min   = Long.MAX_VALUE;
        long        max   = Long.MIN_VALUE;
        long        sum   = 0;
        long        count = 0;
        
        Station(StationName stationName) {
            this.stationName = stationName;
        }
        
        void add(int value) {
            min = min(min, value);
            max = max(max, value);
            sum += value;
            count++;
        }
        
        void add(Station station) {
            min    = min(min, station.min);
            max    = max(max, station.max);
            sum   += station.sum;
            count += station.count;
        }
        
        @Override
        public String toString() {
            // This is copied from the baseline so that the rounding is the same.
            return round(min / 10.0)
                 + "/"
                 + round(((Math.round(sum * 10.0) / 10.0)/count) / 10.0)
                 + "/"
                 + round(max / 10.0);
        }
        private double round(double value) {
            return Math.round(value * 10.0) / 10.0;
        }
    }
    
    /** Statistic data -- the data from the source. This contains data from multiple stations. */
    static class Statistic {
        
        // The max station name is 10,000 but we can start smaller as each extractor only do part of it.
        // We can afford to have it grow as needed.
        static final int STATISTIC_MAP_SIZE = 1024;
        
        final Set<String>               sourceNames = new TreeSet<String>();
        final Map<StationName, Station> stationData;
        
        Statistic(String name, boolean isSorted) {
            if (name != null) {
                sourceNames.add(name);
            }
            
            stationData
                    = isSorted
                    ? new TreeMap<StationName, Station>()
                    : new HashMap<StationName, Station>(STATISTIC_MAP_SIZE);
        }
        
        /** Add value to the station of the same name and return if the station is newly-added. */
        boolean addData(StationName stationName, int value) {
            var isNew   = false;
            var station = stationData.get(stationName);
            if (station == null) {
                station = new Station(stationName);
                stationData.put(stationName, station);
                isNew = true;
            }
            station.add(value);
            return isNew;
        }
        
        /** Absorb all data from the given statistic -- that is add all its source name and data into this one. */
        Statistic absorb(Statistic other) {
            sourceNames.addAll(other.sourceNames);
            for (var entry : other.stationData.entrySet()) {
                var name       = entry.getKey();
                var newStation = entry.getValue();
                stationData.compute(name, (__, existingStation) -> {
                    if (existingStation == null) {
                        return newStation;
                    } else {
                        existingStation.add(newStation);
                        return existingStation;
                    }
                });
            }
            return this;
        }
        
        /** Returns the statistic with sorted data. */
        Statistic sort() {
            if (stationData instanceof TreeMap)
                return this;
            
            return new Statistic(null, true).absorb(this);
        }
        
        @Override
        public String toString() {
            return stationData.toString();
        }
        
    }
    
    /** Extractor to extract data from the buffer.*/
    static record Extractor(
            String     extractorName,
            ByteBuffer buffer,
            long       boundarySize) {
        
        static final int NAME_BUFFER_SIZE = 128;
        
        /**
         * Create an extractor that can read the file from the start to the end.
         * It skip the part of line that should be processed by the previous extractor.
         **/
        static Extractor create(String name, String filePath, long start, long size) throws IOException {
            try (var channel = FileChannel.open(Paths.get(filePath), READ)) {
                // Read a bit longer on the end to ensure that the last line is included.
                // Since the name of the station is at most 100 bytes, 300 extra bytes are read.
                // This because, there can be up to 100+ from the previous and 100+ extended into the next part.
                long sizeToRead = size + 300;
                
                // Get one more in the front to check if the current char is at the beginning of a line.
                // Without the -1, we do not know if the first read bytes are part of the previous extract
                //    or a beginning of this extractor.
                // If the first char is a new line, we know that the next char is the start of the first line.
                // If the first char is not a newline, we know that it was a tail of the previous extract. 
                long startPosition = max(start - 1, 0);
                
                // Calculate the end position.
                long lastPosition = channel.size();
                long endPosition  = min(start + sizeToRead, lastPosition);
                
                // Read the file to the buffer.
                long mapSize = endPosition - startPosition;
                var buffer = channel.map(READ_ONLY, startPosition, mapSize);
                
                // Adjust the start of the buffer by skipping to the next line if not at the start.
                long readBytes = 0;
                if (startPosition != 0) {
                    while (buffer.hasRemaining()) {
                        byte b = buffer.get();
                        readBytes++;
                        if (b == '\n')
                            break;
                    }
                }
                
                // The boundary size shrinks a bit as the start of the line adjusted.
                long boundarySize = size - readBytes;
                return new Extractor(name, buffer, boundarySize);
            }
        }
        
        /** This class help with the extraction as Java does not support multiple-value return. */
        static class NameData {
            byte[] bytes = new byte[NAME_BUFFER_SIZE];
            int    length;
            int    hash1;
            int    hash2;
        }
        
        /** Extract the data from the buffer into statistic -- stations with statistic data. */
        Statistic extract() throws IOException {
            var histogram = new Statistic(extractorName, false);
            
            // Reusable name data for speed -- by reduce object creation.
            var nameData = new NameData();
            
            var startPos  = buffer.position();
            var stopPos   = startPos + boundarySize;
            while (buffer.hasRemaining() && (buffer.position() <= stopPos)) {
                readNameInto(nameData);
                var value = readValue();
                
                var stationName = new StationName(nameData.bytes, nameData.length, nameData.hash1, nameData.hash2);
                var isNew       = histogram.addData(stationName, value);
                if (isNew) {
                    // Since the bytes are now used, we cannot reuse it.
                    nameData = new NameData();
                }
            }
            return histogram;
        }
        
        void readNameInto(NameData nameData) {
            var nameBytes = nameData.bytes;
            int nameIndex = 0;
            int nameHash1 = 1;
            int nameHash2 = 1;
            
            nameIndex = 0;
            while (buffer.hasRemaining()) {
                byte b = buffer.get();
                if (b == ';')
                    break;
                nameBytes[nameIndex++] = b;
                nameHash1 = (nameHash1 << 5) - nameHash1 + b;  // nameHash*31  + b
                nameHash2 = (nameHash2 << 7) - nameHash2 + b;  // nameHash*127 + b
            }
            nameData.length = nameIndex;
            nameData.hash1   = nameHash1;
            nameData.hash2   = nameHash2;
        }
        
        // Read value from the buffer but read it times 10 so we can return as an int.
        // Possible values: -99.0 to 99.9
        // Negative and the first digits are optional.
        // The unit digit, decimal point and the decimal digit are always there.
        int readValue() {
            // Attempt to read the hundredth digit and sign
            var firstCh = buffer.get();
            var sign    = 1;
            var value   = 0;
            if (firstCh == '-') {
                sign  = -1;
                var nextCh = buffer.get();
                value = nextCh - '0';
            } else {
                value = firstCh - '0';
            }
            
            // If point is found the hundredth digit becomes the tenth digit.
            // Otherwise, the hundredth digit is multiplied by 10 -- thus become officially the hundredth digit.
            var secondCh = buffer.get();
            if (secondCh == '.') {
                value = value * 10;
            } else {
                var digit = secondCh - '0';
                value = (value * 10 + digit)*10;
                
                buffer.get(); // '.'
            }
            
            // Getting the decimal digit.
            var thirdCh    = buffer.get();
            int thirdDigit = thirdCh - '0';
            value += thirdDigit;
            
            // Apply the sign.
            value *= sign;
            
            // Read the new line and throw away.
            buffer.get();
            
            return value;
        }
    }
    
    /** Get the size of the file so that we can partition the file for each the extractor. */
    static long fileSize(String filePath) throws IOException {
        try (var channel = FileChannel.open(Paths.get(filePath), READ)) {
            return channel.size();
        }
    }
    
    //== Misc ==
    // These set of classes helps out with the main (not related to the solution).
    
    static interface Run {
        void run() throws IOException, InterruptedException;
    }
    
    static interface Runner {
        void run(Run run) throws IOException, InterruptedException;
    }
    
    static void timedRun(Run run) throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();
        try {
            run.run();
        } finally {
            long endTime = System.currentTimeMillis();
            System.out.println("Time taken: " + (endTime - startTime) + " ms");
        }
    }
    
    static void regularRun(Run execution) throws IOException, InterruptedException {
        execution.run();
    }
    
    //== Main ==
    
    public static void main(String[] args) throws IOException, InterruptedException {
        createRunner(args)
        .run(() -> {
            var filePath   = "measurements.txt";
            var cpuCount   = Runtime.getRuntime().availableProcessors();
            var chunkCount = cpuCount;
            
            var executor   = newFixedThreadPool(cpuCount);
            var statistics = new LinkedBlockingQueue<Statistic>();
            
            // Extract data from the file.
            submitExtractTasks(filePath, chunkCount, executor, statistics);
            // Combined the processed result.
            var statistic = (Statistic)null;
            while (true) {
                statistic = statistics.take();
                
                // No more statistics to combine.
                if ((statistics.size() == 0)
                 && (statistic.sourceNames.size() == chunkCount))
                    break;
                
                var base = statistic;
                var other = statistics.take();
                executor.submit(() -> statistics.add(base.absorb(other)));
            }
            
            System.out.println(statistic.sort());
            executor.shutdownNow();
        });
    }
    
    private static void submitExtractTasks(
            String           filePath,
            int              chunkCount,
            ExecutorService  executor,
            Queue<Statistic> statistics)
                    throws IOException {
        
        long fileSize  = fileSize(filePath);
        long chunkSize = (fileSize / chunkCount) + 1; // Add some buffer to ensure that the entire file is covered.
                                                      // Java round integer division down so the sum of each might be
                                                      //    the actual total.
        for (int i = 0; i < chunkCount; i++) {
            int cpu = i;
            var name = "Extractor-" + cpu;
            executor.submit(() -> {
                try {
                    long pos = cpu*chunkSize;
                    var extractor = Extractor.create(name, filePath, pos, chunkSize);
                    var statistic = extractor.extract();
                    statistics.add(statistic);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    
    static Runner createRunner(String[] args) {
        var timed    = (args.length == 0) || !args[0].equals("--untimed");
        var executor = timed
                     ? (Runner)(CalculateAverage_nawaman_twoHashes::timedRun)
                     : (Runner)(CalculateAverage_nawaman_twoHashes::regularRun);
        return executor;
    }
    
}
