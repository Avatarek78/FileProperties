package properties;

/**
 * @author tomas.langer
 */
public class PropUnknown extends Property {

    PropUnknown(String value, int row) {
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
