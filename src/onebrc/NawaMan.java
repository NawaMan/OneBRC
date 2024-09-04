package onebrc;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.nio.file.StandardOpenOption.READ;
import static java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class NawaMan {
    
    static LongAdder entryCount = new LongAdder();
    
    static double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
    
    static Function<StationStatistic, String> defaultToString = statisic -> {
        return round(statisic.min                            / 10.0) + "/"
                + round((round(statisic.sum)/statisic.count) / 10.0) + "/"
                + round(statisic.max                         / 10.0);
    };
    
    static Function<StationStatistic, String> validateToString = statisic -> {
        return round(statisic.min   / 10.0) + "/"
             + round(statisic.sum   / 10.0) + "/"
             + round(statisic.count /  1.0) + "/"
             + round(statisic.max   / 10.0);
    };
    
    static Function<StationStatistic, String> stationStatisticToString = defaultToString;
    
    static int toDigit(byte nextCh) {
        return nextCh - '0';
    }
    
    static class Buffer{
        ByteBuffer data;
        ByteBuffer slice1;
        ByteBuffer slice2;
        
        Buffer(ByteBuffer data) {
            this.data   = data;
            this.slice1 = data.slice();
            this.slice2 = data.slice();
        }
    }
    
    static class StationName implements Comparable<StationName> {
        
        static final int NAME_BUFFER_SIZE = 128;
        
        private Buffer buffer;
        private int    position;
        private int    length;
        private int    hash;
        private int    id = -1;
        
        private volatile String toString = null;
        
        StationName(Buffer buffer, int position, int length, int hash) {
            this.buffer   = buffer;
            this.position = position;
            this.length   = length;
            this.hash     = hash;
        }
        
        @Override
        public int hashCode() {
            return hash;
        }
        
        @Override
        public boolean equals(Object obj) {
            // Not needed as we are the only one using this class.
//            if (this == obj)
//                return true;
//            if (!(obj instanceof StationName))
//                return false;
            
            var that = (StationName) obj;
            if (length != that.length)
                return false;
            if (hash != that.hash)
                return false;
            
            if (id != -1 && that.id != -1)
                return id == that.id;
            
            if (toString != null && that.toString != null)
                return toString.equals(that.toString);
            
            prepareBuffers(that);
            
            boolean equals = bufferEquals(that);
//            if (!equals) {
//                System.out.println(this.toString() + " != " + that.toString());
//            }
            return equals;
        }
        
        private void prepareBuffers(StationName that) {
            this.buffer.slice1.limit   (this.position + this.length);
            this.buffer.slice1.position(this.position);
            
            that.buffer.slice2.limit   (that.position + that.length);
            that.buffer.slice2.position(that.position);
        }
        
        private boolean bufferEquals(StationName that) {
            return this.buffer.slice1.equals(that.buffer.slice2);
        }
        
        @Override
        public String toString() {
            if (toString == null) {
                synchronized (this) {
                    if (toString == null) {
                        synchronized (buffer) {
                            buffer.slice1.limit   (this.position + this.length);
                            buffer.slice1.position(this.position);
                            toString = StandardCharsets.UTF_8.decode(buffer.slice1).toString();
                        }
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
    
    static class TemperatureBuffer {
        int temperatureTimesTen;
        
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
    
    static class StationStatistic {
        
        StationName stationName;
        long        min   = Long.MAX_VALUE;
        long        max   = Long.MIN_VALUE;
        long        sum   = 0;
        long        count = 0;
        
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
            return stationStatisticToString.apply(this);
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
                        : new ConcurrentHashMap<StationName, StationStatistic>(STATISTIC_MAP_SIZE);
        }
        
        StationStatistic computeIfAbsent(StationName stationName, Function<StationName, StationStatistic> mappingFunction) {
            return stationStatistics.computeIfAbsent(stationName, mappingFunction);
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
                stationStatistics.compute(name, (k, station) -> {
                    if (station == null) {
                        return newStation;
                    }
                    station.include(newStation);
                    return station;
                });
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
    
    static record StatisticExtractor(String extractorName, ByteBuffer buffer) {
        
        static StatisticExtractor create(String name, String filePath, long start, long estimatedSize) throws IOException {
            try (var channel = FileChannel.open(Paths.get(filePath), READ)) {
                // Read a bit longer on the end to ensure that the last line is included.
                // Since the name of the station is at most 100 bytes, 300 extra bytes are read.
                // This because, there can be up to 100+ from the previous and 100+ extended into the next part.
                var tailMargin = 300L;
                var sizeToRead = estimatedSize + tailMargin;
                
                // Get one more byte in the front to check if the newline char is at the beginning of a line.
                // Without this, we do not know if the first read bytes are part of the previous extract
                //    or a beginning of this extractor.
                // If the first char is a new line, we know that the next char is the start of the first line.
                // If the first char is not a newline, we know that it was a tail of the previous extract. 
                long startPosition = max(start - 1, 0);
                
                var lastPosition = channel.size();
                var endPosition  = min(start + sizeToRead, lastPosition);
                var mapSize      = endPosition - startPosition;
                var mappedBuffer = channel.map(FileChannel.MapMode.READ_ONLY, startPosition, mapSize);
                
                var chunkStart = seekStartPosition(mappedBuffer, startPosition);
                var chunkEnd   = seekEndPosition(mappedBuffer, estimatedSize);
                
                mappedBuffer.position((int)chunkStart);
                mappedBuffer.limit   ((int)chunkEnd);
                var slicedBuffer = mappedBuffer.slice();
                slicedBuffer.position(0);
                
                return new StatisticExtractor(name, slicedBuffer);
            } catch (Throwable e) {
                e.printStackTrace();
                var message = "Panic: Failed to read file chunk! filePath=%s, start=%d, estimatedSize=%d"
                        .formatted(filePath, start, estimatedSize);
                throw new Error(message, e);
            }
        }
        
        static long seekStartPosition(ByteBuffer buffer, long startPosition) {
            if (startPosition == 0)
                return 0;
            
            var start = buffer.position();
            findNewLine(buffer);
            
            return buffer.position() - start;
        }
        
        static int seekEndPosition(ByteBuffer buffer, long boundarySize) {
            if (boundarySize > buffer.limit())
                return buffer.limit();
            
            buffer.position((int)boundarySize);
            findNewLine(buffer);
            
            return buffer.position();
        }
        
        static void findNewLine(ByteBuffer buffer) {
            while (buffer.hasRemaining()) {
                if (buffer.get() == '\n')
                    return;
            }
        }
        
        Statistic extract() throws IOException {
            var statistic          = new Statistic(extractorName, false);
            var chunkBuffer        = new Buffer(buffer);
            var temperatureBuffer  = new TemperatureBuffer();
            
            var bytes = new byte[1024];
            var count = 0;
            
            while (buffer.hasRemaining()) {
                var startPosition = buffer.position();
                try {
                    buffer.get(bytes);
                } catch (BufferUnderflowException e) {
                    bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                }
                
                var offset = 0;
                while (offset < bytes.length) {
                    int nameOffset  = offset;
                    var nameHash    = 1;
                    var currentByte = bytes[offset++];  // Name is at lease one character. 
                    for (; offset < bytes.length; ) {
                        currentByte = bytes[offset++];
//                        System.out.println("offset-n: " + (offset - 1) + ", currentByte: " + (char)currentByte);
                        nameHash = (nameHash << 6) - nameHash + currentByte;  // nameHash*127 + b
                        if (currentByte == ';') {
                            break;
                        }
                    }
                    if (bytes[offset - 1] != ';') {   // Done before finding ';'.
                        buffer.position(startPosition + nameOffset);
                        break;
                    }
                    if (offset >= bytes.length) {   // Done before finding next.
                        buffer.position(startPosition + nameOffset);
                        break;
                    }
                    int nameLength = offset - nameOffset - 1;
                    
                    currentByte = bytes[offset++];
//                    System.out.println("offset-v: " + (offset - 1) + ", currentByte: " + (char)currentByte);
                    int sign        = (0 - ((currentByte & 0b0001000) >>> 3)) | 1; //
//                    int sign        = (currentByte == '-') ? -1 : 1;
                    int valueOffset = offset + ((currentByte & 0b0001000) >>> 3) - 1;
//                    int valueOffset = (currentByte == '-') ? offset : offset - 1;
                    
                    for (; offset < bytes.length; ) {
                        currentByte = bytes[offset++];
//                        System.out.println("offset-v: " + (offset - 1) + ", currentByte: " + (char)currentByte);
                        if (currentByte == '\n') {
                            break;
                        }
                    }
                    if (bytes[offset - 1] != '\n') {   // Done before finding '\n'.
                        buffer.position(startPosition + nameOffset);
                        break;
                    }
                    
//                    var name  = new String(bytes, nameOffset, nameLength, StandardCharsets.UTF_8);
                    int namePosition = startPosition + nameOffset;
                    var stationName  = new StationName(chunkBuffer, namePosition, nameLength, nameHash);
                    
                    var valueLength = offset - valueOffset;
                    int value = (100 * toDigit(bytes[valueOffset]) * (valueLength - 4)
                                + 10 * toDigit(bytes[offset - 4])
                                +  1 * toDigit(bytes[offset - 2])) * sign;
//                    if (!name.equals(stationName.toString())) {
//                        System.out.println("name: " + name + ", stationName: " + stationName.toString() + ", value: " + value);
//                    }
                    var station = statistic.computeIfAbsent(stationName, StationStatistic::new);
                    station.add(temperatureBuffer.temperatureTimesTen);
                    
                    count++;
                }
                
                
                
//                
//                var nameLength  = startPosition + i - namePosition - 1;
//                
//                temperatureBuffer.readFrom(bytes);
//                
//                var stationName = new StationName(chunkBuffer, namePosition, nameLength, nameHash);
//                var station     = statistic.computeIfAbsent(stationName, StationStatistic::new);
//                station.add(temperatureBuffer.temperatureTimesTen);
//                
//                namePosition = buffer.position();
//                nameHash     = 1;
//                break;
//                
//                int namePosition = buffer.position();
//                var nameHash     = 1;
//                while (true) {
//                    var b = buffer.get();
//                    if (b == ';') {
//                        var nameLength  = buffer.position() - namePosition - 1;
//                        
//                        temperatureBuffer.readFrom(buffer);
//                        
//                        var stationName = new StationName(chunkBuffer, namePosition, nameLength, nameHash);
//                        var station     = statistic.computeIfAbsent(stationName, StationStatistic::new);
//                        station.add(temperatureBuffer.temperatureTimesTen);
//                        
//                        namePosition = buffer.position();
//                        nameHash     = 1;
//                        break;
//                    }
//                    nameHash = (nameHash << 7) - nameHash + b;  // nameHash*127 + b
//                }
            }
            
//            nameCount.add(statistic.stationStatistics.size());
//            return statistic;

            entryCount.add(count);
            return null;
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        useValidateToStringIfSpecified(args);
        
        var startTime = System.currentTimeMillis();
        
        var filePath   = "measurements.txt";
        var cpuCount   = 8; //Runtime.getRuntime().availableProcessors();
//        var chunkCount = 32*cpuCount;
        var chunkCount = 4*cpuCount;
        
//        var inLoopCount = 0;
//        for (int loop = 0; loop < chunkCount; loop++) {
//        
        var executor   = newVirtualThreadPerTaskExecutor();
        var statistics = new LinkedBlockingQueue<Statistic>();
        
//        int i = 0;
        for (var extractionTask : extractionTasks(filePath, chunkCount, (statistic) -> {}/* statistics.add(statistic)*/ )) {
//            if (i != 0)
//                continue;
//            i++;
            executor.submit(extractionTask);
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);
        
        System.out.println("Time: " + (System.currentTimeMillis() - startTime) + "ms");
        System.out.println("Entries: " + entryCount);
    }
    
    private static void useValidateToStringIfSpecified(String[] args) {
        if (args.length > 0) {
            Stream.of(args).forEach(arg -> {
                if (arg.equals("--validate")) {
                    stationStatisticToString = validateToString;
                }
            });
        }
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
        var chunkName = "Chunk-%03d".formatted(chunkIndex);
        try {
            var extractor = StatisticExtractor.create(chunkName, filePath, position, chunkSize);
            var statistic = extractor.extract();
            accepter.accept(statistic);
        } catch (Throwable e) {
            e.printStackTrace();
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
            var message = "Panic: Failed to get file size! filePath=%s".formatted(filePath);
            throw new Error(message, e);
        }
    }
    
}
