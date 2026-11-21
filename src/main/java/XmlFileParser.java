import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlFileParser implements FileParser {

    @Override
    public List<AddressRecord> parse(File file) throws Exception {
        List<AddressRecord> records = new ArrayList<>();
        XMLInputFactory factory = XMLInputFactory.newInstance();

        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            XMLStreamReader reader = factory.createXMLStreamReader(inputStream, "UTF-8");

            while (reader.hasNext()) {
                int event = reader.next();

                if (event == XMLStreamConstants.START_ELEMENT && "item".equals(reader.getLocalName())) {
                    String city = reader.getAttributeValue(null, "city");
                    String street = reader.getAttributeValue(null, "street");
                    String house = reader.getAttributeValue(null, "house");
                    String floorStr = reader.getAttributeValue(null, "floor");

                    if (city != null && street != null && house != null && floorStr != null) {
                        int floor = Integer.parseInt(floorStr.trim());
                        records.add(new AddressRecord(city.trim(), street.trim(), house.trim(), floor));
                    }
                }
            }
            reader.close();
        }
        return records;
    }
}

