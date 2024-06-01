package util.models;

import exception.GenericCastException;

public class GenericValue implements IGenericValue {
    private Object value;

    public <TValue> void setValue(TValue value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public <TValue> TValue getAndCast() throws GenericCastException {
        if (value == null) {
            return null;
        }

        try {
            return (TValue)value;
        }
        catch (ClassCastException ex) {
            TValue v = null;
            throw new GenericCastException("Could not cast to: %s", v.getClass().getName());
        }
    }

    public static <TValue> GenericValue of(TValue value) {
        var genericValue = new GenericValue();

        genericValue.setValue(value);

        return genericValue;
    }
}