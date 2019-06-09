package properties;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Basic class for property value. Value is comparable by row number.
 * @author tomas.langer
 */
@Data
@AllArgsConstructor
public class Property {

    private String value;
    private Integer row;

    public final boolean isPropValue() {
        return this instanceof PropValue;
    }

    public final boolean isPropEmptyLine() {
        return this instanceof PropEmptyLine;
    }

    public final boolean isPropComment() {
        return this instanceof PropComment;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "value='" + value + '\'' +
                ", row=" + row +
                '}';
    }
}
