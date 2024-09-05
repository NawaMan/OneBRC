package onebrc;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.nio.file.StandardOpenOption.READ;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor;

import static java.util.stream.IntStream.range;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
public class CalculateAverage_nawa_1 {
    
    final static double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
    
    final static int toDigit(byte nextCh) {
        return nextCh - '0';
    }
    
    static LongAdder totalBytes   = new LongAdder();
    static LongAdder totalEntries = new LongAdder();
    
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
        
        void extract() throws IOException {
            // Checks how much bytes are read and compare to the file size.
//            System.out.println(extractorName + ": " + buffer.limit());
//            totalBytes.add(buffer.limit());
            
            var loopBytes = new byte[4096];
            var nameBytes = new byte[128];
            
            // Main loop
            var bytesStartPosition = 0;
            while (buffer.hasRemaining()) {
                buffer.position(bytesStartPosition);
                loopBytes = readBytes(buffer, loopBytes);
                
//                // Checks total read using bytes.
//                totalBytes.add(loopBytes.length);
                
                var loopBytesIndex = 0;
                int nameStartIndex = 0;
                int nameByteIndex  = 0;
                int nameHash       = 1;
                
                // 5 is the minimum length of value from ';' to newline.
                while (loopBytesIndex < (loopBytes.length - 5)) {
                    byte currentByte = loopBytes[loopBytesIndex++];
                    if (currentByte != ';') {
                        nameBytes[nameByteIndex++] = currentByte;
                        nameHash = (nameHash << 6) - nameHash + currentByte;
                        continue;
                    }
                    
                    var nameLength = loopBytesIndex - nameStartIndex - 1;
                    
                    // Found the end of the name.
                    // There at least 4 more bytes to read.
                    // So this is safe to read an extra byte without checking.
                    currentByte = loopBytes[loopBytesIndex++];
                    int valueSign    =  (currentByte == '-') ? -1 :  1;
                    int valueOffset  = ((currentByte == '-') ?  0 : -1) + loopBytesIndex;
                    
                    while (loopBytesIndex < loopBytes.length) {
                        currentByte = loopBytes[loopBytesIndex++];
                        if (currentByte == '\n') {
                            var valueLength = loopBytesIndex - valueOffset;
                            var value = (100 * toDigit(loopBytes[valueOffset]) * (valueLength - 4)
                                        + 10 * toDigit(loopBytes[loopBytesIndex - 4])
                                        +  1 * toDigit(loopBytes[loopBytesIndex - 2])) * valueSign;
                            var name  = new String(loopBytes, nameStartIndex, nameLength, StandardCharsets.UTF_8);
//                            System.out.println("name: " + name + ", value: " + value);
                            
                            // Move the processing bytes to the next line.
                            bytesStartPosition += (loopBytesIndex - nameStartIndex);
                            nameStartIndex      =  loopBytesIndex;
                            nameByteIndex       = 0;
                            nameHash            = 1;
                            totalEntries.increment();
                            break;
                        }
                    }
                }
                
            }
            
                    
//                        
//                        nameStartIndex = loopBytesIndex;    // Mark successful parse of a row.
//                        nameByteIndex  = 0;
//                        nameLength     = 0;
//                        nameHash       = 1;
//                    } else {
//                        nameBytes[nameByteIndex++] = currentByte;
//                        nameHash = (nameHash << 6) - nameHash + currentByte;
//                    }
//                }
//                
//                // Not enough bytes to finish the parsing, reset to the last know start entry.
//                buffer.position(loopStartPosition + nameStartIndex);
//                    
////                            
////                            for (; offset < bytes.length; ) {
////                                currentByte = bytes[offset++];
//////                                System.out.println("offset-v: " + (offset - 1) + ", currentByte: " + (char)currentByte);
////                                if (currentByte == '\n') {
////                                    break;
////                                }
////                            }
////                            if (bytes[offset - 1] != '\n') {   // Done before finding '\n'.
////                                buffer.position(startPosition + nameOffset);
////                                break;
////                            }
////                            
//////                            var name  = new String(bytes, nameOffset, nameLength, StandardCharsets.UTF_8);
//////                            int namePosition = startPosition + nameOffset;
////                            
////                            var valueLength = offset - valueOffset;
////                            int value = (100 * toDigit(bytes[valueOffset]) * (valueLength - 4)
////                                        + 10 * toDigit(bytes[offset - 4])
////                                        +  1 * toDigit(bytes[offset - 2])) * sign;
//////                            if (!name.equals(stationName.toString())) {
//////                                System.out.println("name: " + name + ", stationName: " + stationName.toString() + ", value: " + value);
//////                            }
//////                            var station = statistic.computeIfAbsent(stationName, StationStatistic::new);
//////                            station.add(temperatureBuffer.temperatureTimesTen);
////                            
////                            stationName.length = nameLength;
////                            stationName.hash   = nameHash;
////                            
////                            var station = statistic.computeIfAbsent(stationName, StationStatistic::new);
////                            station.add(value);
////                            
////                            if (station.stationName == stationName) {
////                                // Add to station queue so that we can assign ID to it to make the merge much faster.
////                                stationNameQueue.add(station.stationName);
////                                
////                                // The buffer is not reusable once it is used in the map, so we need to create a new one.
////                                stationName = new StationName();
////                            }
////                            
////                        }
////                        
////                        name[index++] = currentByte;
////                        nameHash = (nameHash << 6) - nameHash + currentByte;
////                    }
////                    if (bytes[offset - 1] != ';') {   // Done before finding ';'.
////                        buffer.position(startPosition + nameOffset);
////                        break;
////                    }
////                    if (offset >= bytes.length) {   // Done before finding next.
////                        buffer.position(startPosition + nameOffset);
////                        break;
////                    }
//////                    count++;
//            }
////            
////            
////            
////            
////            
////            
////            
////            
////            
////            
////            
//////            var statistic         = new Statistic(extractorName, false);
//////            var stationNameBuffer = new StationName();
//////            var temperatureBuffer = new TemperatureBuffer();
//////            var startPosition     = buffer.position();
//////            var stopPosition      = startPosition + boundarySize;
//////            while (buffer.hasRemaining() && (buffer.position() <= stopPosition)) {
//////                stationNameBuffer.readFrom(buffer);
//////                temperatureBuffer.readFrom(buffer);
//////                
//////                var station = statistic.computeIfAbsent(stationNameBuffer, StationStatistic::new);
//////                station.add(temperatureBuffer.temperatureTimesTen);
//////                
//////                if (station.stationName == stationNameBuffer) {
//////                    // Add to station queue so that we can assign ID to it to make the merge much faster.
//////                    stationNameQueue.add(station.stationName);
//////                    
//////                    // The buffer is not reusable once it is used in the map, so we need to create a new one.
//////                    stationNameBuffer = new StationName();
//////                }
//////            }
////            return statistic;
        }
        
        private static byte[] readBytes(ByteBuffer buffer, byte[] bytes) {
            // Try is cheap but check is expensive.
            // Check every time is also expensive
            //     so we don't check and just catch the exception which will happen only once.
            try {
                buffer.get(bytes);
            } catch (BufferUnderflowException e) {
                bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
            }
            return bytes;
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        var startTime = System.currentTimeMillis();
        
        var filePath   = "measurements.txt";
        var cpuCount   = Runtime.getRuntime().availableProcessors();
        var chunkCount = cpuCount;
        
        var executor = newVirtualThreadPerTaskExecutor();
        for (var extractionTask : extractionTasks(filePath, chunkCount)) {
            executor.submit(extractionTask);
        }
        
        executor.shutdown();
        executor.awaitTermination(3, TimeUnit.MINUTES);
        
        
//        // Checks how much bytes are read and compare to the file size
//        System.out.println("File size    : " + fileSize(filePath));
//        System.out.println("Total Bytes  : " + totalBytes);
        
        // Checks how many entries are processed.
        System.out.println("Total Entries: " + totalEntries);
        
        System.out.println("Time: " + (System.currentTimeMillis() - startTime) + "ms");
        Thread.sleep(1000);
    }
    
    static Runnable[] extractionTasks(String filePath, int chunkCount) {
        long fileSize  = fileSize(filePath);
        long chunkSize = (fileSize / chunkCount) + 1; // Add some buffer to ensure that the entire file is covered.
                                                      // Java round integer division down so the sum of each might be
                                                      //    the actual total.
        return range     (0, chunkCount)
                .mapToObj(extractionTask(filePath, chunkSize))
                .toArray (Runnable[]::new);
    }
    
    static IntFunction<Runnable> extractionTask(String filePath, long chunkSize) {
        return (int chunkIndex) -> {
            return () -> {
                var position = chunkIndex*chunkSize;
                try {
                    var chunkName = "Chunk-%03d".formatted(chunkIndex);
                    var extractor = StatisticExtractor.create(chunkName, filePath, position, chunkSize);
                    extractor.extract();
                } catch (IOException e) {
                    var message
                            = "Panic: Failed to read file chunk! filePath=%s, chunkIndex=%d, chunkSize=%d, position=%d"
                            .formatted(filePath, chunkIndex, chunkSize, position);
                    throw new Error(message, e);
                }
            };
        };
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