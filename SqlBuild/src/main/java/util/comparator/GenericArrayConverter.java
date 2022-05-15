package util.comparator;

import util.ArrayGenericValueFinder;

public final class GenericArrayConverter {

    public static <TGenericValue> TGenericValue[] fromGenericElementsToArray(TGenericValue... values) {
        if(values == null) {
            throw new IllegalArgumentException("Invalid parameter/s");
        }

        if(values.length == 0) {
            return null;
        }

        if(ArrayGenericValueFinder.contains(values, null)) {
            throw new IllegalArgumentException("Sequence cannot contain null values");
        }

        return values;
    }
}
