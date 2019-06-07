import properties.*;

import java.io.FileReader;
import java.io.IOException;

/**
 * @author tomas.langer
 */
public class Main {

    public static void main(String[] args) {
        FileProperties fileProperties = new FileProperties();
        try {
            fileProperties.load(new FileReader("test.properties"));
            fileProperties.entrySet().forEach(System.out::println);
            System.out.println();
            fileProperties.entrySet().forEach(entry -> {
                String key = entry.getKey();
                Property value = entry.getValue();
                if (value instanceof PropValue) {
                    System.out.print(value.getRow());
                    System.out.print("\t" + key);
                    System.out.println("=" + value.getValue());
                } else if (value instanceof PropEmptyLine) {
                    System.out.println(value.getRow());
                } else if (value instanceof PropComment) {
                    System.out.print(value.getRow());
                    System.out.println("\t" + value.getValue());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
