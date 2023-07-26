package util;

import util.comparator.GenericComparator;
import validator.IValidator;

import java.util.Arrays;

public final class ArrayGenericValueFinder {

    public static <TValue> int find(TValue[] values, TValue value) {
        for(int i = 0; i < values.length; ++i) {
            if(values[i] == value) {
                return i;
            }
        }

        return -1;
    }

    public static <TValue> boolean contains(TValue[] values, TValue value) {
        return find(values, value) != -1;
    }

    public static <TValue> int find(TValue[] values, IValidator<TValue> validator) {
        ensureValuesAreValid(values);

        for(int index=0; index < values.length; ++index) {
            if(validator.validate(values[index])) {
                return index;
            }
        }

        return -1;
    }

    public static <TValue> int search(TValue[] values, TValue value) {
        ensureValuesAreValid(values, value);

        if(values.length == 0) {
            return -1;
        }

        var comparator = new GenericComparator<TValue>();

        Arrays.sort(values);

       return binarySearch(comparator, values, value, 0, values.length - 1);
    }

    private static <TValue> int binarySearch(GenericComparator<TValue> comparator, TValue[] values, TValue value, int min, int max) {
        int mid = min + (max - min) / 2;

        if(comparator.isGreater(values[mid], value)) {
            return binarySearch(comparator, values, value, min + 1, max);
        }

        if(comparator.isLessThan(values[mid], value)) {
            return binarySearch(comparator, values, value, min, mid - 1);
        }

        if(comparator.isEqual(values[mid], value)) {
            return mid;
        }

        return -1;
    }

    private static <TValue> void ensureValuesAreValid(TValue[] values) {
        ensureValues(values, null, false);
    }

    private static <TValue> void ensureValuesAreValid(TValue[] values, TValue value) {
        ensureValues(values, value, true);
    }

    private static <TValue> void ensureValues(TValue[] values, TValue value, boolean validate) {
        if(values == null) {
            throw new IllegalArgumentException("Invalid array");
        }

        if(validate) {
            if(value == null) {
                throw new IllegalArgumentException("Invalid value");
            }
        }
    }
}
