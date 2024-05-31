package util;

public class GenericValue {
    private Object value;

    public <TValue> void setValue(TValue value) {
        this.value = (Object)value;
    }

    public <TValue> TValue getAndCast() {
        if (value == null) {
            return null;
        }

        return (TValue)value;
    }

    public static <TValue> GenericValue of(TValue value) {
        var genericValue = new GenericValue();

        genericValue.setValue(value);

        return genericValue;
    }
}
