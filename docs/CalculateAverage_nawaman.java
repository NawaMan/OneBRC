package onebrc;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.nio.channels.FileChannel.MapMode.READ_ONLY;
import static java.nio.file.StandardOpenOption.READ;
import static java.util.concurrent.Executors.newFixedThreadPool;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
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

/**
 * One-Billion Row Challenge solution by NawaMan.
 * 
 * Key Points:
 * <ol>
 * <li>Mapped file to memory for ask IO access.</li>
 * <li>Parallel process of data in chunks.</li>
 * <li>Chunk separation with minimum overlaps.</li>
 * <li>Store name in a fix length byte array 
 *         so we don't need to find out the length before create the array.</li>
 * <li>Parse the temperature into integer (x10) rather than float/double.</li>
 * <li>Reuse name array if one already exists in the collection.</li>
 * <li>Pre-calculate hashCode when parsing (finding out start and end).</li>
 * </ol>
 */
public class CalculateAverage_nawaman {
    
    static int cpuCount   = Runtime.getRuntime().availableProcessors();
    static int chunkCount = cpuCount;
    
    static ExecutorService                executor   = newFixedThreadPool(cpuCount);
    static LinkedBlockingQueue<Statistic> statistics = new LinkedBlockingQueue<Statistic>();
    
    /**
     * Station name
     * <ol>
     *   <li>store name as bytes with cached hashCode for used as a map key.</li>
     *   <li>convert to string (cached) when needed proper UTF-8 sorting.</li>
     * </ol>
     **/
    static class StationName implements Comparable<StationName> {
        final byte[] nameArray;
        final int    nameLength;
        final int    hashCode;
        
        private volatile String toString = null;
        
        StationName(byte[] nameArray, int nameLength, int nameHash) {
            this.nameArray  = nameArray;
            this.nameLength = nameLength;
            this.hashCode   = nameHash;
        }
        
        @Override
        public int hashCode() {
            return hashCode;
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
            if (hashCode() != other.hashCode())
                return false;
            
            return Arrays.equals(nameArray, 0, nameLength, other.nameArray, 0, nameLength);
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
    
    /** Station statistic -- This contains statistic from a single station. */
    static class StationStatistic {
        StationName stationName;
        long        min   = Long.MAX_VALUE;
        long        max   = Long.MIN_VALUE;
        long        sum   = 0;
        long        count = 0;
        
        static double round(double value) {
            return Math.round(value * 10.0) / 10.0;
        }
        
        StationStatistic(StationName stationName) {
            this.stationName = stationName;
        }
        
        /** Add the measurement to the statistic. */
        void add(int measurement) {
            min = min(min, measurement);
            max = max(max, measurement);
            sum += measurement;
            count++;
        }
        
        /** Include and combine the data from the given station statistic into this one. */
        void include(StationStatistic stationStatistic) {
            min    = min(min, stationStatistic.min);
            max    = max(max, stationStatistic.max);
            sum   += stationStatistic.sum;
            count += stationStatistic.count;
        }
        
        @Override
        public String toString() {
            // This is copied from the baseline so that the rounding is the same.
            return round(min                / 10.0) + "/"
                 + round((round(sum)/count) / 10.0) + "/"
                 + round(max                / 10.0);
        }
    }
    
    /** Statistic of multiple station -- This contains data from multiple stations and multiple chunk. */
    static class Statistic {
        
        // The max station name is 10,000 but we can start smaller as each extractor only do part of it.
        // We can afford to have it grow as needed.
        static final int STATISTIC_MAP_SIZE = 1024;
        
        final Set<String>                        chunkNames = new TreeSet<String>();
        final Map<StationName, StationStatistic> stationData;
        
        Statistic(String name, boolean isSorted) {
            if (name != null) {
                chunkNames.add(name);
            }
            
            stationData
                    = isSorted
                    ? new TreeMap<StationName, StationStatistic>()
                    : new HashMap<StationName, StationStatistic>(STATISTIC_MAP_SIZE);
        }
        
        /** Add value to the station of the same name and return if the station is newly-added. */
        boolean addMeasurement(StationName stationName, int measurement) {
            var isNew   = false;
            var station = stationData.get(stationName);
            // Interestingly, using compute(...) is slower than the get and if-else.
            if (station == null) {
                station = new StationStatistic(stationName);
                stationData.put(stationName, station);
                isNew = true;
            }
            station.add(measurement);
            return isNew;
        }
        
        /** Include and absorb all data from the given statistic */
        Statistic include(Statistic other) {
            chunkNames.addAll(other.chunkNames);
            for (var entry : other.stationData.entrySet()) {
                var name       = entry.getKey();
                var newStation = entry.getValue();
                var station    = stationData.get(name);
                // Interestingly, using compute(...) is slower than the get and if-else.
                if (station == null) {
                    stationData.put(name, newStation);
                } else {
                    station.include(newStation);
                }
            }
            return this;
        }
        
        /** Returns the statistic with sorted data. */
        Statistic sort() {
            if (stationData instanceof TreeMap)
                return this;
            
            return new Statistic(null, true).include(this);
        }
        
        @Override
        public String toString() {
            return stationData.toString();
        }
        
    }
    
    /** Extractor to extract data from the buffer.*/
    static record ChunkExtractor(
            String     extractorName,
            ByteBuffer buffer,
            long       boundarySize) {
        
        static final int NAME_BUFFER_SIZE = 128;
        
        private static int toDigit(byte nextCh) {
            return nextCh - '0';
        }
        
        /**
         * Create an extractor that can read the file from the start to the end.
         * It skip the part of line that should be processed by the previous extractor.
         **/
        static ChunkExtractor create(String name, String filePath, long start, long size) throws IOException {
            try (var channel = FileChannel.open(Paths.get(filePath), READ)) {
                // Read a bit longer on the end to ensure that the last line is included.
                // Since the name of the station is at most 100 bytes, 300 extra bytes are read.
                // This because, there can be up to 100+ from the previous and 100+ extended into the next part.
                long sizeToRead = size + 300;
                
                // Calculate the start position.
                // Get one more byte in the front to check if the newline char is at the beginning of a line.
                // Without this, we do not know if the first read bytes are part of the previous extract
                //    or a beginning of this extractor.
                // If the first char is a new line, we know that the next char is the start of the first line.
                // If the first char is not a newline, we know that it was a tail of the previous extract. 
                long startPosition = max(start - 1, 0);
                
                // Calculate the end position.
                long lastPosition = channel.size();
                long endPosition  = min(start + sizeToRead, lastPosition);
                
                // Read the chunk of the file to the buffer.
                long mapSize = endPosition - startPosition;
                var buffer = channel.map(READ_ONLY, startPosition, mapSize);
                
                // Adjust the start of the buffer by skipping to the next line if not at the start.
                long skippedBytes = seekToStart(startPosition, buffer);
                
                // The boundary size shrinks because the starting point is adjusted.
                long boundarySize = size - skippedBytes;
                return new ChunkExtractor(name, buffer, boundarySize);
            }
        }
        
        private static long seekToStart(long startPosition, MappedByteBuffer buffer) {
            long skippedBytes = 0;
            if (startPosition != 0) {
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    skippedBytes++;
                    if (b == '\n')
                        break;
                }
            }
            return skippedBytes;
        }
        
        /** This class help with the extraction as Java does not support multiple-value return. */
        static class StationNameData {
            byte[] bytes = new byte[NAME_BUFFER_SIZE];
            int    length;
            int    hash;
        }
        
        /** Extract the data from the buffer into statistic -- stations with statistic data. */
        Statistic extract() throws IOException {
            var histogram = new Statistic(extractorName, false);
            
            // Reusable station-name data for speed -- by reduce object creation.
            var stationNameData = new StationNameData();
            
            var startPos = buffer.position();
            var stopPos  = startPos + boundarySize;
            while (buffer.hasRemaining() && (buffer.position() <= stopPos)) {
                readStationName(stationNameData);
                var value = readTemperature();
                
                var stationName = new StationName(stationNameData.bytes, stationNameData.length, stationNameData.hash);
                var isNew       = histogram.addMeasurement(stationName, value);
                if (isNew) {
                    // The station name is found to be a new one(for this chunk) so it is now used in the map.
                    // Since the object are now used, we cannot reuse it.
                    // Therefore, we need to create a new one.
                    stationNameData = new StationNameData();
                }
            }
            return histogram;
        }
        
        void readStationName(StationNameData nameData) {
            var nameBytes = nameData.bytes;
            int nameIndex = 0;
            int nameHash  = 1;
            
            nameIndex = 0;
            while (buffer.hasRemaining()) {
                byte b = buffer.get();
                if (b == ';')
                    break;
                nameBytes[nameIndex++] = b;
                nameHash = (nameHash << 5) - nameHash + b;  // nameHash*31 + b
            }
            nameData.length = nameIndex;
            nameData.hash   = nameHash;
        }
        
        // Read temperature from the buffer but read it as times-10 integer representation.
        // Possible values: -99.0 to 99.9 will now become -990 to 999.
        int readTemperature() {
            var sign        = 1;
            var temperature = 0;
            // Attempt to read the first digit and sign
            var firstChar = buffer.get();
            if (firstChar == '-') {
                // Found negative sign
                sign = -1;
                
                // Read the next one for the hundredth digit.
                temperature = toDigit(buffer.get());
            } else {
                temperature = toDigit(firstChar);
            }
            
            // If the decimal point is found the first digit becomes the tenth digit.
            // Otherwise, the first digit the hundredth digit and the second is the tenth digit.
            var secondCh = buffer.get();
            if (secondCh == '.') {
                temperature = temperature * 10;
            } else {
                var digit = toDigit(secondCh);
                temperature = (temperature * 10 + digit)*10;
                
                buffer.get(); // Read and throw away the decimal point ('.')
            }
            
            // Getting the digit after the decimal point.
            int thirdDigit = toDigit(buffer.get());
            temperature += thirdDigit;
            
            // Apply the sign.
            temperature *= sign;
            
            // Read and throw away the new line ('\n').
            buffer.get();
            
            return temperature;
        }
    }
    
    //== Main ==
    
    public static void main(String[] args) throws IOException, InterruptedException {
        createRunner(args).run(() -> {
            var filePath = "measurements.txt";
            
            for (var chunkReadTask : chunkExtractTasks(filePath)) {
                executor.submit(chunkReadTask);
            }
            
            var statistic = (Statistic)null;
            while (true) {
                statistic = statistics.take();
                
                // No more statistics to combine.
                if ((statistics.size() == 0)
                 && (statistic.chunkNames.size() == chunkCount))
                    break;
                
                var base = statistic;
                var other = statistics.take();
                executor.submit(() -> statistics.add(base.include(other)));
            }
            
            System.out.println(statistic.sort());
            executor.shutdownNow();
        });
    }
    
    /** Create the tasks to extract the data from the file. */
    private static Runnable[] chunkExtractTasks(String filePath) throws IOException {
        long fileSize  = fileSize(filePath);
        long chunkSize = (fileSize / chunkCount) + 1; // Add some buffer to ensure that the entire file is covered.
                                                      // Java round integer division down so the sum of each might be
                                                      //    the actual total.
        var runnables = new Runnable[chunkCount];
        for (int i = 0; i < chunkCount; i++) {
            int cpuIndex  = i;
            var chunkName = "Chunk-" + i;
            runnables[i] = (() -> {
                try {
                    long pos = cpuIndex*chunkSize;
                    var extractor = ChunkExtractor.create(chunkName, filePath, pos, chunkSize);
                    var statistic = extractor.extract();
                    statistics.add(statistic);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return runnables;
    }
    
    /** Get the size of the file so that we can partition the file for each the extractor. */
    static long fileSize(String filePath) throws IOException {
        try (var channel = FileChannel.open(Paths.get(filePath), READ)) {
            return channel.size();
        }
    }
    
    //== Misc ==
    // These set of classes helps out with the main (not related to the solution).
    // In particular, they allows development-time timing of the solution.
    // During the real run (run-openjdk.sh) the timing is disabled.
    
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
    
    static Runner createRunner(String[] args) {
        var timed    = (args.length == 0) || !args[0].equals("--untimed");
        var executor = timed
                     ? (Runner)(CalculateAverage_nawaman::timedRun)
                     : (Runner)(CalculateAverage_nawaman::regularRun);
        return executor;
    }
    
}
