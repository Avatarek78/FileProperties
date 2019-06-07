package properties;

import com.google.common.io.LineReader;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author tomas.langer
 */
public class FileProperties extends LinkedHashMap<String, Property> {

    public synchronized void load(Reader reader) throws IOException {
        load(new LineReader(reader));
    }

    private void load(LineReader lr) throws IOException {
        int row = 1;
        while (true) {
            String line = lr.readLine();
            if (line == null) {
                break;
            }
            HashMap.Entry<String, Property> entry = parseProperty(line, row);
            put(entry.getKey(), entry.getValue());
            row++;
        }
    }

    private HashMap.Entry<String, Property> parseProperty(String line, int row) {
        line = line.trim();
        if (line.contains("=")) {
            // key=value
            String[] keyAndVal = line.split("=");
            return createPropValueEntry(keyAndVal, row);
        } else if (line.contains(":")) {
            // key:value
            String[] keyAndVal = line.split(":");
            return createPropValueEntry(keyAndVal, row);
        } else if (line.isEmpty()) {
            PropEmptyLine propEmptyLine = new PropEmptyLine("", row);
            return new SimpleEntry<>("" + row, propEmptyLine);
        } else if (line.startsWith("#") || line.startsWith("!")) {
            PropComment propComment = new PropComment(line, row);
            return new SimpleEntry<>("" + row, propComment);
        } else {
            PropUnknown propUnknown = new PropUnknown("", row);
            return new SimpleEntry<>("" + row, propUnknown);
        }
    }

    /**
     * Creates property key and value.
     * @param keyAndVal
     * @param row
     * @return
     */
    private HashMap.Entry<String, Property> createPropValueEntry(String[] keyAndVal, int row) {
        PropValue propValue = new PropValue(keyAndVal[1].trim(), row);
        return new SimpleEntry<>(keyAndVal[0].trim(), propValue);
    }

}
