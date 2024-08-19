package onebrc;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.nio.file.StandardOpenOption.READ;
import static java.util.concurrent.Executors.newFixedThreadPool;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

// Use two hash functions and skip array value comparison when "equals" is called.

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
public class CalculateAverage_nawaman_twoHashes {
    
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
            
//            return Arrays.equals(nameArray, 0, nameLength, other.nameArray, 0, nameLength);
            return true;
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
            // Avoid handling sorting of UTF-8 characters myself so convert it to string.
            return this.toString().compareTo(o.toString());
        }
    }
    
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
        
        void add(int temperatureTimesTen) {
            min = min(min, temperatureTimesTen);
            max = max(max, temperatureTimesTen);
            sum += temperatureTimesTen;
            count++;
        }
        
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
    
    static class Statistic {
        
        // The max station name is 10,000 but we can start smaller as each extractor only do part of it.
        static final int STATISTIC_MAP_SIZE = 1024;
        
        final Set<String>                        chunkNames = new TreeSet<String>();
        final Map<StationName, StationStatistic> stationStatistics;
        
        Statistic(String name, boolean isSorted) {
            if (name != null) {
                chunkNames.add(name);
            }
            
            stationStatistics = isSorted
                        ? new TreeMap<StationName, StationStatistic>()
                        : new HashMap<StationName, StationStatistic>(STATISTIC_MAP_SIZE);
        }
        
        StationStatistic getStationStatistic(StationName stationName) {
            return stationStatistics.get(stationName);
        }
        
        void setStationStatistic(StationStatistic stationStatistic) {
            stationStatistics.put(stationStatistic.stationName, stationStatistic);
        }
        
        Statistic absorb(Statistic other) {
            chunkNames.addAll(other.chunkNames);
            for (var entry : other.stationStatistics.entrySet()) {
                var name       = entry.getKey();
                var newStation = entry.getValue();
                var station    = stationStatistics.get(name);
                // Interestingly, using compute(...) is slower than the get and if-else.
                if (station == null) {
                    stationStatistics.put(name, newStation);
                } else {
                    station.include(newStation);
                }
            }
            return this;
        }
        
        Statistic sorted() {
            return (stationStatistics instanceof TreeMap)
                    ? this
                    : new Statistic(null, true).absorb(this);
        }
        
        @Override
        public String toString() {
            return stationStatistics.toString();
        }
        
    }
    
    static record StatisticExtractor(
            String     extractorName,
            ByteBuffer buffer,
            long       boundarySize) {
        
        static StatisticExtractor create(String name, String filePath, long start, long size) throws IOException {
            try (var channel = FileChannel.open(Paths.get(filePath), READ)) {
                // Read a bit longer on the end to ensure that the last line is included.
                // Since the name of the station is at most 100 bytes, 300 extra bytes are read.
                // This because, there can be up to 100+ from the previous and 100+ extended into the next part.
                var tailMargin = 300L;
                var sizeToRead = size + tailMargin;
                
                // Get one more byte in the front to check if the newline char is at the beginning of a line.
                // Without this, we do not know if the first read bytes are part of the previous extract
                //    or a beginning of this extractor.
                // If the first char is a new line, we know that the next char is the start of the first line.
                // If the first char is not a newline, we know that it was a tail of the previous extract. 
                long startPosition = max(start - 1, 0);
                
                var lastPosition = channel.size();
                var endPosition  = min(start + sizeToRead, lastPosition);
                var mapSize      = endPosition - startPosition;
                var buffer       = channel.map(FileChannel.MapMode.READ_ONLY, startPosition, mapSize);
                var skippedBytes = seekToStart(startPosition, buffer);
                var boundarySize = size - skippedBytes;
                return new StatisticExtractor(name, buffer, boundarySize);
            }
        }
        
        static long seekToStart(long startPosition, ByteBuffer buffer) {
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
        
        Statistic extract() throws IOException {
            var statistic         = new Statistic(extractorName, false);
            var stationNameBuffer = new StationNameBuffer();
            var temperatureBuffer = new TemperatureBuffer();
            var startPosition     = buffer.position();
            var stopPosition      = startPosition + boundarySize;
            while (buffer.hasRemaining() && (buffer.position() <= stopPosition)) {
                stationNameBuffer.readFrom(buffer);
                temperatureBuffer.readFrom(buffer);
                
                var stationName = new StationName(stationNameBuffer.bytes, stationNameBuffer.length, stationNameBuffer.hash1, stationNameBuffer.hash2);
                
                // Interestingly, using compute(...) is slower than the get and if-else.
                var stationStatistic = statistic.getStationStatistic(stationName);
                if (stationStatistic == null) {
                    stationStatistic = new StationStatistic(stationName);
                    statistic.setStationStatistic(stationStatistic);
                    
                    // The buffer is used in the station name, so we cannot reuse it. Hence, we need to create a new one.
                    stationNameBuffer = new StationNameBuffer();
                }
                
                stationStatistic.add(temperatureBuffer.temperatureTimesTen);
            }
            return statistic;
        }
    }
    
    static class StationNameBuffer {
        
        static final int NAME_BUFFER_SIZE = 128;
        
        byte[] bytes = new byte[NAME_BUFFER_SIZE];
        int    length;
        int    hash1;
        int    hash2;
        
        void readFrom(ByteBuffer buffer) {
            var bytes     = this.bytes;
            int nameIndex = 0;
            int nameHash1 = 1;
            int nameHash2 = 1;
            
            nameIndex = 0;
            while (buffer.hasRemaining()) {
                byte b = buffer.get();
                if (b == ';')
                    break;
                bytes[nameIndex++] = b;
                nameHash1 = (nameHash1 << 5) - nameHash1 + b;  // nameHash*31  + b
                nameHash1 = (nameHash1 << 8) - nameHash1 + b;  // nameHash*127 + b
            }
            
            this.bytes  = bytes;
            this.length = nameIndex;
            this.hash1  = nameHash1;
            this.hash2  = nameHash2;
        }
    }
    
    static class TemperatureBuffer {
        int temperatureTimesTen;
        
        static int toDigit(byte nextCh) {
            return nextCh - '0';
        }
        
        void readFrom(ByteBuffer buffer) {
            var sign        = 1;
            var temperature = 0;
            
            var firstChar = buffer.get();
            if (firstChar == '-') {
                sign = -1;  // Found negative sign
                temperature = toDigit(buffer.get());
            } else {
                temperature = toDigit(firstChar);
            }
            
            var secondCh = buffer.get();
            if (secondCh != '.') {
                temperature = temperature * 10 + toDigit(secondCh);
                buffer.get(); // Read and throw away the decimal point ('.')
            }
            
            temperature = temperature * 10;
            
            // Getting the digit after the decimal point.
            temperature += toDigit(buffer.get());
            
            buffer.get();   // Read and throw away the new line ('\n').
            
            this.temperatureTimesTen = sign * temperature;
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        var filePath   = "measurements.txt";
        var cpuCount   = Runtime.getRuntime().availableProcessors();
        var chunkCount = cpuCount;
        
        var executor   = newFixedThreadPool(cpuCount);
        var statistics = new LinkedBlockingQueue<Statistic>();
        
        for (var extractionTask : extractionTasks(filePath, chunkCount, (statistic) -> statistics.add(statistic))) {
            executor.submit(extractionTask);
        }
        
        var statistic = (Statistic)null;
        while (true) {
            statistic = statistics.take();
            
            var lastInQueue      = statistics.size() == 0;
            var includeAllChunks = statistic.chunkNames.size() == chunkCount;
            var isLastStatistic  =  lastInQueue && includeAllChunks;
            if (isLastStatistic) {
                break;
            }
            
            var baseStatistic  = statistic;
            var otherStatistic = statistics.take();
            executor.submit(() -> statistics.add(baseStatistic.absorb(otherStatistic)));
        }
        executor.shutdownNow();
        
        System.out.println(statistic.sorted());
        executor.shutdownNow();
    }
    
    static Runnable[] extractionTasks(String filePath, int chunkCount, Consumer<Statistic> resultAccepter) {
        long fileSize  = fileSize(filePath);
        long chunkSize = (fileSize / chunkCount) + 1; // Add some buffer to ensure that the entire file is covered.
                                                      // Java round integer division down so the sum of each might be
                                                      //    the actual total.
        var runnables = new Runnable[chunkCount];
        for (int i = 0; i < chunkCount; i++) {
            int cpuIndex  = i;
            runnables[i] = (() -> extractionTask(filePath, cpuIndex, chunkSize, resultAccepter));
        }
        return runnables;
    }
    
    static void extractionTask(String filePath, int chunkIndex, long chunkSize, Consumer<Statistic> accepter) {
        var position  = chunkIndex*chunkSize;
        var chunkName = "Chunk-" + chunkIndex;
        try {
            var extractor = StatisticExtractor.create(chunkName, filePath, position, chunkSize);
            var statistic = extractor.extract();
            accepter.accept(statistic);
        } catch (IOException e) {
            var message
                    = "Panic: Failed to read file chunk! filePath=%s, chunkIndex=%d, chunkSize=%d, position=%d"
                    .formatted(filePath, chunkIndex, chunkSize, position);
            throw new Error(message, e);
        }
    }
    
    static long fileSize(String filePath) {
        try (var channel = FileChannel.open(Paths.get(filePath), READ)) {
            return channel.size();
        } catch (IOException e) {
            var message = "Panic: Failed to get file size! filePath=%d".formatted(filePath);
            throw new Error(message, e);
        }
    }
}