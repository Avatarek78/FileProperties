import properties.*;

import java.io.FileReader;
import java.io.IOException;

/**
 * @author tomas.langer
 */
public class Main {

    public static void main(String[] args) {
        long stTime = System.currentTimeMillis();
        FileProperties fileProperties = new FileProperties();
        try {
            fileProperties.load(new FileReader("test.properties"));
//            fileProperties.load(new FileReader("messages.properties"));
            fileProperties.entrySet().forEach(System.out::println);
            System.out.println();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Run time: " + (System.currentTimeMillis() - stTime) + " ms");
    }

}
