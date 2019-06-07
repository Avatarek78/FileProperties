package properties;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tomas.langer
 */
@Data
@AllArgsConstructor
public class Property {

    private String value;
    private int row;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "value='" + value + '\'' +
                ", row=" + row +
                '}';
    }
}
