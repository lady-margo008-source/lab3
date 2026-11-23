import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
    public class CsvFileParser implements FileParser {

        @Override
        public List<AddressRecord> parse(File file) throws Exception {
            List<AddressRecord> records = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

                String line = reader.readLine();

                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }

                    String[] parts = line.split(";");
                    if (parts.length < 4) {
                        continue;
                    }

                    String city = cleanValue(parts[0]);
                    String street = cleanValue(parts[1]);
                    String house = cleanValue(parts[2]);
                    int floor = Integer.parseInt(cleanValue(parts[3]));

                    records.add(new AddressRecord(city, street, house, floor));
                }
            }
            return records;
        }

        private String cleanValue(String raw) {
            return raw.trim().replaceAll("^\"|\"$", "");
        }
    }
