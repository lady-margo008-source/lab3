import java.io.File;
import java.util.List;
public interface FileParser {
    List<AddressRecord> parse(File file) throws Exception;
}
