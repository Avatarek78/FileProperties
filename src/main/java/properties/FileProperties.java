package properties;

import com.google.common.io.LineReader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author tomas.langer
 */
public class FileProperties extends LinkedHashMap<String, Property> {

    /**
     * @param reader
     * @throws IOException
     */
    public void load(Reader reader) throws IOException {
        loadByLineReader(new LineReader(reader));
    }

    public void save(Writer writer) throws IOException {
        saveByBufferedWriter((writer instanceof BufferedWriter)?(BufferedWriter)writer : new BufferedWriter(writer));
    }

    private void loadByLineReader(LineReader lr) throws IOException {
        synchronized (this) {
            int row = 1;
            while (true) {
                String line = lr.readLine();
                if (line == null) {
                    break;
                }
                Map.Entry<String, Property> entry = parseProperty(line, row);
                put(entry.getKey(), entry.getValue());
                row++;
            }
        }
    }

    public void saveByBufferedWriter(BufferedWriter bw) throws IOException {
        synchronized (this) {
            for (Map.Entry<String, Property> e : entrySet()) {
                String key = e.getKey();
                Property value = e.getValue();
                if (value.isPropValue()) {
                    bw.write(key + "=" + e.getValue().getValue());
                    bw.newLine();
                    continue;
                }
                if (value.isPropEmptyLine()) {
                    bw.newLine();
                    continue;
                }
                if (value.isPropComment()) {
                    bw.write(e.getValue().getValue());
                    bw.newLine();
                    continue;
                }
            }
        }
        bw.flush();
    }

    private Map.Entry<String, Property> parseProperty(String line, int row) {
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
            PropUnknown propUnknown = new PropUnknown(line, row);
            return new SimpleEntry<>("" + row, propUnknown);
        }
    }

    /**
     * Creates property key and value.
     * @param keyAndVal
     * @param row
     * @return
     */
    private Map.Entry<String, Property> createPropValueEntry(String[] keyAndVal, int row) {
        PropValue propValue = new PropValue(keyAndVal[1].trim(), row);
        return new SimpleEntry<>(keyAndVal[0].trim(), propValue);
    }

}
