import properties.FileProperties;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author tomas.langer
 */
public class Main {

    public static void main(String[] args) {
        long stTime = System.currentTimeMillis();
        FileProperties fileProperties = new FileProperties();
        try {
            // Set encoding is maybe not necessary, but in case it is needed...
//            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("test.properties"), StandardCharsets.UTF_8);
            fileProperties.load(new FileReader("test.properties"));
            fileProperties.entrySet().forEach(System.out::println);
            System.out.println();
            System.out.println("Formatted (like in file) output:");
//            // Test of sorting by row number. Switch order (rows) of this two lines:
//            fileProperties.get("propA").setRow(3);
//            fileProperties.get("propC").setRow(1);
//            // and re-sort map by row number...
//            FileProperties sortedFileProperties = fileProperties.entrySet().stream()
//                    .sorted(Comparator.comparing(o -> o.getValue().getRow()))
//                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, FileProperties::new));
            fileProperties.forEach((key, value) -> {
                if (value.isPropValue()) {
                    System.out.print(value.getRow());
                    System.out.print("\t" + key);
                    System.out.println("=" + value.getValue());
                } else if (value.isPropEmptyLine()) {
                    System.out.println(value.getRow());
                } else if (value.isPropComment()) {
                    System.out.print(value.getRow());
                    System.out.println("\t" + value.getValue());
                }
            });
            System.out.println("Load time: " + (System.currentTimeMillis() - stTime) + " ms");
            stTime = System.currentTimeMillis();
            FileWriter fileWriter = new FileWriter("test-out.properties");
            fileProperties.save(fileWriter);
            System.out.println("Save time: " + (System.currentTimeMillis() - stTime) + " ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
