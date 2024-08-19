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
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

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
        
        void add(int measurement) {
            min = min(min, measurement);
            max = max(max, measurement);
            sum += measurement;
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
        // We can afford to have it grow as needed.
        static final int STATISTIC_MAP_SIZE = 1024;
        
        final Set<String>                        chunkNames = new TreeSet<String>();
        final Map<StationName, StationStatistic> stationData;
        
        Statistic(String name, boolean isSorted) {
            if (name != null) {
                chunkNames.add(name);
            }
            
            stationData = isSorted
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
        
        Statistic absorb(Statistic other) {
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
        
        Statistic sorted() {
            return (stationData instanceof TreeMap)
                    ? this
                    : new Statistic(null, true).absorb(this);
        }
        
        @Override
        public String toString() {
            return stationData.toString();
        }
        
    }
    
    static record StatisticExtractor(
            String     extractorName,
            ByteBuffer buffer,
            long       boundarySize) {
        
        static final int NAME_BUFFER_SIZE = 128;
        
        static int toDigit(byte nextCh) {
            return nextCh - '0';
        }
        
        static StatisticExtractor create(String name, String filePath, long start, long size) throws IOException {
            try (var channel = FileChannel.open(Paths.get(filePath), READ)) {
                // Read a bit longer on the end to ensure that the last line is included.
                // Since the name of the station is at most 100 bytes, 300 extra bytes are read.
                // This because, there can be up to 100+ from the previous and 100+ extended into the next part.
                var tailMargin = 300L;
                var sizeToRead = size + tailMargin;
                
                // Calculate the start position.
                // Get one more byte in the front to check if the newline char is at the beginning of a line.
                // Without this, we do not know if the first read bytes are part of the previous extract
                //    or a beginning of this extractor.
                // If the first char is a new line, we know that the next char is the start of the first line.
                // If the first char is not a newline, we know that it was a tail of the previous extract. 
                long startPosition = max(start - 1, 0);
                
                var lastPosition = channel.size();
                var endPosition  = min(start + sizeToRead, lastPosition);
                var mapSize      = endPosition - startPosition;
                var buffer       = channel.map(READ_ONLY, startPosition, mapSize);
                var skippedBytes = seekToStart(startPosition, buffer);
                var boundarySize = size - skippedBytes;
                return new StatisticExtractor(name, buffer, boundarySize);
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
        static class StationNameBuffer {
            byte[] bytes = new byte[NAME_BUFFER_SIZE];
            int    length;
            int    hash;
            
            void readFrom(ByteBuffer buffer) {
                int nameIndex = 0;
                int nameHash  = 1;
                
                nameIndex = 0;
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    if (b == ';')
                        break;
                    bytes[nameIndex++] = b;
                    nameHash = (nameHash << 5) - nameHash + b;  // nameHash*31 + b
                }
                length = nameIndex;
                hash   = nameHash;
            }
        }
        
        Statistic extract() throws IOException {
            var statistic  = new Statistic(extractorName, false);
            var nameBuffer = new StationNameBuffer();
            var startPos   = buffer.position();
            var stopPos    = startPos + boundarySize;
            while (buffer.hasRemaining() && (buffer.position() <= stopPos)) {
                nameBuffer.readFrom(buffer);
                var temperatureTimesTen = readTemperatureTimesTen();
                
                var stationName = new StationName(nameBuffer.bytes, nameBuffer.length, nameBuffer.hash);
                var isNew       = statistic.addMeasurement(stationName, temperatureTimesTen);
                if (isNew) {
                    // The station name is found to be a new one(for this chunk) so it is now used in the map.
                    // Since the object are now used, we cannot reuse it.
                    // Therefore, we need to create a new one.
                    nameBuffer = new StationNameBuffer();
                }
            }
            return statistic;
        }
        
        int readTemperatureTimesTen() {
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
            
            return sign * temperature;
        }
    }
    
    //== Main ==
    
    public static void main(String[] args) throws InterruptedException {
        var startTime = System.currentTimeMillis();
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
            
            var base = statistic;
            var other = statistics.take();
            executor.submit(() -> statistics.add(base.absorb(other)));
        }
        executor.shutdownNow();
        
        System.out.println(statistic.sorted());
        executor.shutdownNow();
        var endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
        
        var actual = statistic.sorted().toString();
        var expected = "{Abha=-34.0/18.0/66.3, Abidjan=-21.9/26.0/77.0, Abéché=-22.9/29.4/78.8, Accra=-21.2/26.4/75.6, Addis Ababa=-32.5/16.0/64.8, Adelaide=-32.3/17.3/67.9, Aden=-35.8/29.1/89.2, Ahvaz=-21.3/25.4/76.4, Albuquerque=-35.4/14.0/66.0, Alexandra=-42.2/11.0/60.7, Alexandria=-28.7/20.0/71.3, Algiers=-31.0/18.2/71.2, Alice Springs=-30.4/21.0/70.1, Almaty=-41.4/10.0/58.2, Amsterdam=-38.0/10.2/62.8, Anadyr=-61.9/-6.9/47.2, Anchorage=-47.0/2.8/53.6, Andorra la Vella=-39.9/9.8/61.5, Ankara=-39.5/12.0/61.0, Antananarivo=-35.3/17.9/66.4, Antsiranana=-26.4/25.2/72.4, Arkhangelsk=-48.0/1.3/50.5, Ashgabat=-36.5/17.1/66.0, Asmara=-33.7/15.6/70.9, Assab=-20.9/30.5/84.2, Astana=-43.9/3.5/57.7, Athens=-30.5/19.2/73.2, Atlanta=-38.8/17.0/66.3, Auckland=-34.5/15.2/64.9, Austin=-30.3/20.7/72.4, Baghdad=-27.7/22.8/70.7, Baguio=-31.5/19.5/70.4, Baku=-35.9/15.1/67.0, Baltimore=-42.2/13.1/59.8, Bamako=-19.1/27.8/78.2, Bangkok=-24.4/28.6/78.0, Bangui=-21.2/26.0/73.5, Banjul=-28.3/26.0/78.5, Barcelona=-29.0/18.2/67.6, Bata=-24.8/25.1/74.9, Batumi=-35.0/14.0/61.4, Beijing=-38.5/12.9/63.6, Beirut=-27.2/20.9/76.3, Belgrade=-38.1/12.5/62.0, Belize City=-27.4/26.7/75.5, Benghazi=-27.9/19.9/71.7, Bergen=-44.1/7.7/61.1, Berlin=-37.8/10.3/68.9, Bilbao=-35.0/14.7/63.5, Birao=-29.0/26.5/75.7, Bishkek=-38.4/11.3/58.8, Bissau=-28.4/27.0/74.5, Blantyre=-26.4/22.2/71.2, Bloemfontein=-41.4/15.6/63.5, Boise=-38.7/11.4/56.5, Bordeaux=-33.4/14.2/65.3, Bosaso=-23.9/30.0/80.3, Boston=-35.3/10.9/63.6, Bouaké=-22.5/26.0/79.3, Bratislava=-37.1/10.5/60.3, Brazzaville=-24.9/25.0/77.4, Bridgetown=-21.9/27.0/76.5, Brisbane=-26.9/21.4/69.8, Brussels=-40.5/10.5/63.1, Bucharest=-38.6/10.8/60.6, Budapest=-39.3/11.3/59.9, Bujumbura=-23.4/23.8/73.8, Bulawayo=-30.4/18.9/65.6, Burnie=-36.7/13.1/66.1, Busan=-33.3/15.0/67.8, Cabo San Lucas=-23.8/23.9/76.3, Cairns=-24.9/25.0/81.2, Cairo=-32.7/21.4/73.7, Calgary=-46.2/4.4/49.9, Canberra=-42.2/13.1/61.6, Cape Town=-37.7/16.2/66.6, Changsha=-31.4/17.4/66.7, Charlotte=-30.4/16.1/64.2, Chiang Mai=-24.2/25.8/79.6, Chicago=-40.9/9.8/61.5, Chihuahua=-33.6/18.6/66.2, Chittagong=-21.8/25.9/73.9, Chișinău=-40.7/10.2/59.0, Chongqing=-34.5/18.6/68.4, Christchurch=-34.8/12.2/64.6, City of San Marino=-37.6/11.8/63.2, Colombo=-27.2/27.4/82.0, Columbus=-37.0/11.7/62.4, Conakry=-22.0/26.4/80.3, Copenhagen=-40.4/9.1/62.4, Cotonou=-25.6/27.2/79.4, Cracow=-39.1/9.3/59.9, Da Lat=-34.6/17.9/69.3, Da Nang=-23.1/25.8/77.8, Dakar=-26.3/24.0/72.1, Dallas=-30.7/19.0/76.4, Damascus=-31.1/17.0/63.4, Dampier=-23.4/26.4/79.2, Dar es Salaam=-23.0/25.8/73.3, Darwin=-24.1/27.6/75.1, Denpasar=-24.1/23.7/72.3, Denver=-37.6/10.4/58.0, Detroit=-36.3/10.0/65.6, Dhaka=-25.3/25.9/75.8, Dikson=-58.0/-11.1/36.0, Dili=-25.2/26.6/74.9, Djibouti=-21.0/29.9/77.3, Dodoma=-25.9/22.7/70.5, Dolisie=-30.4/24.0/71.9, Douala=-20.7/26.7/75.3, Dubai=-20.0/26.9/80.4, Dublin=-39.5/9.8/60.2, Dunedin=-39.2/11.1/62.3, Durban=-33.3/20.6/70.2, Dushanbe=-35.2/14.7/64.0, Edinburgh=-43.0/9.3/61.3, Edmonton=-44.6/4.2/53.2, El Paso=-34.1/18.1/68.4, Entebbe=-28.1/21.0/67.7, Erbil=-29.4/19.5/68.1, Erzurum=-46.6/5.1/53.5, Fairbanks=-52.2/-2.3/47.1, Fianarantsoa=-30.7/17.9/72.9, Flores,  Petén=-27.0/26.4/77.9, Frankfurt=-42.6/10.6/59.8, Fresno=-32.3/17.9/67.8, Fukuoka=-34.2/17.0/73.7, Gaborone=-30.1/21.0/71.0, Gabès=-27.7/19.5/70.6, Gagnoa=-23.9/26.0/74.0, Gangtok=-32.0/15.2/66.2, Garissa=-20.5/29.3/82.5, Garoua=-19.7/28.3/76.5, George Town=-22.9/27.9/77.7, Ghanzi=-27.4/21.4/70.2, Gjoa Haven=-69.6/-14.4/34.1, Guadalajara=-29.0/20.9/72.3, Guangzhou=-29.4/22.4/76.6, Guatemala City=-27.2/20.4/68.2, Halifax=-41.7/7.5/59.8, Hamburg=-40.7/9.7/59.7, Hamilton=-37.3/13.8/65.3, Hanga Roa=-32.3/20.5/69.5, Hanoi=-25.3/23.6/76.3, Harare=-31.1/18.4/67.1, Harbin=-44.3/5.0/54.8, Hargeisa=-26.6/21.7/70.1, Hat Yai=-28.1/27.0/79.8, Havana=-28.0/25.2/75.8, Helsinki=-42.2/5.9/59.4, Heraklion=-30.0/18.9/73.3, Hiroshima=-32.3/16.3/70.3, Ho Chi Minh City=-21.9/27.4/76.3, Hobart=-38.8/12.7/60.4, Hong Kong=-29.9/23.3/73.6, Honiara=-22.6/26.5/77.3, Honolulu=-22.9/25.4/74.6, Houston=-28.0/20.8/71.2, Ifrane=-42.1/11.4/62.1, Indianapolis=-46.3/11.8/62.7, Iqaluit=-58.5/-9.3/39.6, Irkutsk=-50.9/1.0/49.7, Istanbul=-38.5/13.9/65.4, Jacksonville=-32.4/20.3/70.5, Jakarta=-24.5/26.7/78.0, Jayapura=-24.7/27.0/73.6, Jerusalem=-33.3/18.3/69.9, Johannesburg=-35.7/15.5/71.7, Jos=-25.3/22.8/82.5, Juba=-18.0/27.8/75.5, Kabul=-37.2/12.1/62.5, Kampala=-34.9/20.0/69.1, Kandi=-21.4/27.7/75.8, Kankan=-20.7/26.5/73.5, Kano=-25.6/26.4/74.9, Kansas City=-35.3/12.5/61.3, Karachi=-26.5/26.0/74.1, Karonga=-22.5/24.4/73.3, Kathmandu=-31.6/18.3/70.8, Khartoum=-22.8/29.9/80.5, Kingston=-23.4/27.4/81.1, Kinshasa=-25.8/25.3/78.5, Kolkata=-24.3/26.7/77.8, Kuala Lumpur=-24.0/27.3/78.0, Kumasi=-31.7/26.0/76.2, Kunming=-31.8/15.7/67.0, Kuopio=-47.6/3.4/56.0, Kuwait City=-24.9/25.7/73.1, Kyiv=-43.4/8.4/59.4, Kyoto=-33.9/15.8/65.6, La Ceiba=-24.8/26.2/76.9, La Paz=-29.2/23.7/74.1, Lagos=-24.3/26.8/78.5, Lahore=-27.9/24.3/74.2, Lake Havasu City=-25.5/23.7/72.1, Lake Tekapo=-39.3/8.7/58.6, Las Palmas de Gran Canaria=-32.2/21.2/79.6, Las Vegas=-27.9/20.3/69.8, Launceston=-38.0/13.1/65.9, Lhasa=-41.8/7.6/57.6, Libreville=-22.7/25.9/78.6, Lisbon=-35.5/17.5/66.3, Livingstone=-28.5/21.8/79.6, Ljubljana=-37.8/10.9/61.6, Lodwar=-20.1/29.3/76.6, Lomé=-25.4/26.9/76.3, London=-45.0/11.3/60.9, Los Angeles=-33.0/18.6/68.6, Louisville=-33.0/13.9/62.1, Luanda=-22.4/25.8/78.3, Lubumbashi=-29.8/20.8/73.6, Lusaka=-29.1/19.9/70.9, Luxembourg City=-41.0/9.3/59.0, Lviv=-45.2/7.8/56.4, Lyon=-41.1/12.5/59.4, Madrid=-36.3/15.0/67.6, Mahajanga=-23.1/26.3/76.0, Makassar=-23.3/26.7/78.1, Makurdi=-24.8/26.0/74.2, Malabo=-21.4/26.3/80.2, Malé=-24.4/28.0/76.6, Managua=-31.9/27.3/77.0, Manama=-25.2/26.5/75.9, Mandalay=-26.6/28.0/82.8, Mango=-21.2/28.1/83.8, Manila=-20.0/28.4/81.4, Maputo=-32.4/22.8/75.3, Marrakesh=-33.6/19.6/71.3, Marseille=-37.5/15.8/65.5, Maun=-25.6/22.4/75.3, Medan=-24.7/26.5/76.7, Mek'ele=-26.9/22.7/70.1, Melbourne=-34.1/15.1/66.3, Memphis=-30.7/17.2/68.6, Mexicali=-24.1/23.1/74.1, Mexico City=-38.0/17.5/68.3, Miami=-22.4/24.9/78.2, Milan=-37.3/13.0/64.6, Milwaukee=-42.5/8.9/58.4, Minneapolis=-40.8/7.8/58.7, Minsk=-44.8/6.7/53.7, Mogadishu=-22.7/27.1/75.8, Mombasa=-30.2/26.3/77.0, Monaco=-39.7/16.4/64.6, Moncton=-47.0/6.1/55.9, Monterrey=-29.0/22.3/72.3, Montreal=-39.3/6.8/58.9, Moscow=-45.4/5.8/56.3, Mumbai=-23.5/27.1/76.4, Murmansk=-50.0/0.6/51.0, Muscat=-22.1/28.0/77.1, Mzuzu=-30.4/17.7/66.4, N'Djamena=-22.1/28.3/81.6, Naha=-24.6/23.1/72.3, Nairobi=-34.6/17.8/65.7, Nakhon Ratchasima=-25.5/27.3/79.6, Napier=-36.8/14.6/72.8, Napoli=-35.2/15.9/63.6, Nashville=-35.5/15.4/63.5, Nassau=-26.2/24.6/72.9, Ndola=-29.8/20.3/69.8, New Delhi=-24.1/25.0/80.2, New Orleans=-28.3/20.7/76.0, New York City=-35.7/12.9/62.2, Ngaoundéré=-28.5/22.0/71.4, Niamey=-19.0/29.3/77.9, Nicosia=-30.5/19.7/74.5, Niigata=-37.1/13.9/61.9, Nouadhibou=-26.6/21.3/72.8, Nouakchott=-24.7/25.7/81.6, Novosibirsk=-47.7/1.7/57.4, Nuuk=-50.1/-1.4/47.7, Odesa=-39.7/10.7/60.9, Odienné=-21.6/26.0/76.4, Oklahoma City=-33.1/15.9/64.9, Omaha=-44.5/10.6/57.7, Oranjestad=-21.0/28.1/77.5, Oslo=-44.1/5.7/59.3, Ottawa=-44.0/6.6/55.9, Ouagadougou=-24.6/28.3/83.2, Ouahigouya=-18.8/28.6/74.7, Ouarzazate=-28.9/18.9/71.3, Oulu=-45.1/2.7/51.3, Palembang=-21.4/27.3/76.0, Palermo=-30.0/18.5/68.2, Palm Springs=-23.2/24.5/74.3, Palmerston North=-34.5/13.2/59.4, Panama City=-20.4/28.0/84.7, Parakou=-23.4/26.8/78.8, Paris=-35.1/12.3/59.2, Perth=-34.7/18.7/69.0, Petropavlovsk-Kamchatsky=-45.3/1.9/50.5, Philadelphia=-40.4/13.2/63.9, Phnom Penh=-21.5/28.3/76.6, Phoenix=-23.3/23.9/74.5, Pittsburgh=-40.9/10.8/59.4, Podgorica=-35.1/15.3/63.7, Pointe-Noire=-24.0/26.1/76.6, Pontianak=-24.2/27.7/77.7, Port Moresby=-23.0/26.9/79.1, Port Sudan=-24.5/28.4/76.1, Port Vila=-21.9/24.3/79.2, Port-Gentil=-21.8/26.0/77.7, Portland (OR)=-39.7/12.4/65.0, Porto=-33.9/15.7/66.1, Prague=-42.7/8.4/57.9, Praia=-30.9/24.4/78.5, Pretoria=-36.6/18.2/66.6, Pyongyang=-40.9/10.8/66.4, Rabat=-34.7/17.2/67.5, Rangpur=-26.4/24.4/72.8, Reggane=-21.1/28.3/75.3, Reykjavík=-45.1/4.3/53.9, Riga=-43.1/6.2/59.9, Riyadh=-23.6/26.0/81.2, Rome=-35.6/15.2/68.0, Roseau=-22.0/26.2/74.8, Rostov-on-Don=-39.4/9.9/58.1, Sacramento=-33.6/16.3/66.8, Saint Petersburg=-45.5/5.8/62.8, Saint-Pierre=-43.3/5.7/53.7, Salt Lake City=-40.1/11.6/62.8, San Antonio=-31.2/20.8/69.8, San Diego=-34.0/17.8/70.1, San Francisco=-34.5/14.6/65.4, San Jose=-32.1/16.4/63.0, San José=-26.7/22.6/74.0, San Juan=-21.2/27.2/84.3, San Salvador=-27.8/23.1/71.1, Sana'a=-27.2/20.0/68.4, Santo Domingo=-24.6/25.9/76.6, Sapporo=-44.0/8.9/55.5, Sarajevo=-40.4/10.1/57.9, Saskatoon=-45.9/3.3/53.0, Seattle=-37.0/11.3/61.9, Seoul=-35.8/12.5/59.0, Seville=-34.9/19.2/67.0, Shanghai=-34.8/16.7/67.8, Singapore=-23.3/27.0/80.9, Skopje=-38.8/12.4/62.0, Sochi=-35.3/14.2/65.7, Sofia=-39.6/10.6/61.3, Sokoto=-20.4/28.0/78.8, Split=-35.5/16.1/63.0, St. John's=-44.6/5.0/56.6, St. Louis=-34.8/13.9/63.0, Stockholm=-48.3/6.6/54.6, Surabaya=-21.7/27.1/80.6, Suva=-21.9/25.6/79.9, Suwałki=-39.3/7.2/54.3, Sydney=-30.7/17.7/66.2, Ségou=-28.2/28.0/75.9, Tabora=-35.7/23.0/73.9, Tabriz=-36.2/12.6/62.8, Taipei=-28.5/23.0/70.5, Tallinn=-45.2/6.4/56.8, Tamale=-22.3/27.9/84.9, Tamanrasset=-27.7/21.7/72.8, Tampa=-27.2/22.9/73.7, Tashkent=-36.6/14.8/64.8, Tauranga=-32.5/14.8/64.0, Tbilisi=-44.8/12.9/60.2, Tegucigalpa=-28.8/21.7/73.6, Tehran=-29.8/17.0/64.0, Tel Aviv=-26.9/20.0/70.0, Thessaloniki=-34.3/16.0/70.0, Thiès=-26.4/24.0/71.3, Tijuana=-29.8/17.8/67.3, Timbuktu=-19.3/28.0/83.2, Tirana=-31.9/15.2/67.4, Toamasina=-24.4/23.4/70.5, Tokyo=-37.0/15.4/63.7, Toliara=-25.3/24.1/73.2, Toluca=-41.1/12.4/62.5, Toronto=-37.3/9.4/61.2, Tripoli=-27.7/20.0/70.2, Tromsø=-49.8/2.9/58.8, Tucson=-32.0/20.9/80.0, Tunis=-32.3/18.4/68.2, Ulaanbaatar=-48.7/-0.4/50.8, Upington=-28.9/20.4/72.5, Vaduz=-37.8/10.1/57.9, Valencia=-28.9/18.3/67.3, Valletta=-36.1/18.8/69.2, Vancouver=-40.0/10.4/59.8, Veracruz=-22.1/25.4/76.6, Vienna=-38.4/10.4/57.0, Vientiane=-22.8/25.9/80.3, Villahermosa=-28.4/27.1/76.9, Vilnius=-46.2/6.0/59.1, Virginia Beach=-36.1/15.8/65.9, Vladivostok=-45.0/4.9/64.8, Warsaw=-40.2/8.5/57.8, Washington, D.C.=-45.0/14.6/65.8, Wau=-25.4/27.8/76.6, Wellington=-37.4/12.9/64.9, Whitehorse=-47.0/-0.1/48.9, Wichita=-35.6/13.9/64.5, Willemstad=-24.3/28.0/78.0, Winnipeg=-48.6/3.0/52.0, Wrocław=-39.7/9.6/60.4, Xi'an=-38.1/14.1/65.0, Yakutsk=-59.8/-8.8/41.1, Yangon=-25.9/27.5/79.9, Yaoundé=-25.6/23.8/74.4, Yellowknife=-52.2/-4.3/46.4, Yerevan=-34.5/12.4/60.3, Yinchuan=-39.9/9.0/56.7, Zagreb=-44.2/10.7/58.7, Zanzibar City=-26.5/26.0/72.5, Zürich=-38.2/9.3/62.4, Ürümqi=-46.4/7.4/56.1, İzmir=-32.4/17.9/67.6}";
        if (!actual.equals(expected))
            throw new AssertionError("Expected: " + expected + " but got: " + actual);
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
            throw new Error("Panic: Failed to get file size! filePath=" + filePath, e);
        }
    }
}
