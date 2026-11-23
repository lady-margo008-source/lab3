import java.io.File;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);
    private final StatisticsService statisticsService = new StatisticsService();

    public void start() {
        System.out.println("=== АНАЛИЗАТОР СПРАВОЧНИКОВ ГОРОДОВ (CSV / XML) ===");
        System.out.println("Для выхода из программы введите 'exit' или 'выход'.");

        while (true) {
            System.out.print("\nВведите путь до файла-справочника (.csv или .xml): ");
            String input = scanner.nextLine().trim();

            input = input.replaceAll("^\"|\"$", "");

            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("выход")) {
                System.out.println("Завершение работы программы. До свидания!");
                break;
            }

            if (input.isEmpty()) {
                System.out.println("Путь к файлу не может быть пустым.");
                continue;
            }

            processFile(input);
        }
    }

    private void processFile(String filePath) {
        File file = new File(filePath);

        if (!file.exists() || !file.isFile()) {
            System.out.println("Файл не найден по указанному пути: " + file.getAbsolutePath());
            return;
        }

        FileParser parser;
        String lowerName = file.getName().toLowerCase();
        if (lowerName.endsWith(".csv")) {
            parser = new CsvFileParser();
        } else if (lowerName.endsWith(".xml")) {
            parser = new XmlFileParser();
        } else {
            System.out.println("Неподдерживаемый формат файла. Используйте .csv или .xml");
            return;
        }

        try {
            System.out.println("Чтение и анализ файла...");

            long startTime = System.nanoTime();

            List<AddressRecord> records = parser.parse(file);
            statisticsService.calculateAndPrintStatistics(records);

            long endTime = System.nanoTime();
            double elapsedMs = (endTime - startTime) / 1_000_000_000.0;

            System.out.printf("\n=== 3. ВРЕМЯ ОБРАБОТКИ ФАЙЛА ===\nВсего обработано записей: %d\nЗатраченное время: %.3f с\n",
                    records.size(), elapsedMs);
            System.out.println("==========================================");

        } catch (Exception e) {
            String errorMsg = (e.getMessage() != null) ? e.getMessage() : e.getClass().getSimpleName();
            System.out.println("Не удалось обработать файл: " + errorMsg);
            System.out.println("Проверьте целостность структуры файла и попробуйте снова.");
        }
    }
}

