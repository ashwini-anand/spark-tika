
import java.util.HashMap;
import java.util.Map;

/**
 * Created by asanand on 2/17/17.
 */
public class MetadataStore {
    private Map<String, String> metadata = new HashMap<String, String>();

    public Map<String, String> getMetadata() {
        return metadata;
    }

    @Override
    public String toString() {
        String str = "";
        for (String key : metadata.keySet()
                ) {
            str += key + " = " + metadata.get(key) + "\n";
        }
        str += "----------------End of metadata of this file-------------------------\n\n";
        return str;
    }
}
