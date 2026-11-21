import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsService {

    public void calculateAndPrintStatistics(List<AddressRecord> records) {
        Map<AddressRecord, Integer> recordCounts = new HashMap<>();

        Map<String, int[]> cityFloors = new HashMap<>();

        for (AddressRecord record : records) {
            recordCounts.put(record, recordCounts.getOrDefault(record, 0) + 1);

            int floor = record.getFloor();
            if (floor >= 1 && floor <= 5) {
                int[] floorsArray = cityFloors.computeIfAbsent(record.getCity(), k -> new int[5]);
                floorsArray[floor - 1]++;
            }
        }

        printDuplicates(recordCounts);
        printCityFloorStats(cityFloors);
    }

    private void printDuplicates(Map<AddressRecord, Integer> recordCounts) {
        System.out.println("\n=== 1. ДУБЛИРУЮЩИЕСЯ ЗАПИСИ ===");
        boolean hasDuplicates = false;

        for (Map.Entry<AddressRecord, Integer> entry : recordCounts.entrySet()) {
            if (entry.getValue() > 1) {
                System.out.println(entry.getKey() + " — повторений: " + entry.getValue());
                hasDuplicates = true;
            }
        }

        if (!hasDuplicates) {
            System.out.println("Дубликаты не найдены.");
        }
    }

    private void printCityFloorStats(Map<String, int[]> cityFloors) {
        System.out.println("\n=== 2. СТАТИСТИКА ЭТАЖНОСТИ ПО ГОРОДАМ ===");

        for (Map.Entry<String, int[]> entry : cityFloors.entrySet()) {
            String city = entry.getKey();
            int[] floors = entry.getValue();

            System.out.printf("г. %-18s | 1-эт: %4d | 2-эт: %4d | 3-эт: %4d | 4-эт: %4d | 5-эт: %4d%n",
                    city, floors[0], floors[1], floors[2], floors[3], floors[4]);
        }
    }
}

