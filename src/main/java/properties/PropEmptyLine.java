package properties;

/**
 * @author tomas.langer
 */
public class PropEmptyLine extends Property {

    PropEmptyLine(String value, int row) {
        super(value, row);
    }

    @Override
    public void setValue(String value) {
    }

    @Override
    public String getValue() {
        return "";
    }
}
