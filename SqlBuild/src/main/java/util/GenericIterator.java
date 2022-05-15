package util;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class GenericIterator {
    public static <TValue extends Object> void each(TValue[] values, Consumer<TValue> actionPredicate) {
        validate(values);

        for(TValue value: values) {
            actionPredicate.accept(value);
        }
    }

    public static <TValue extends Object> boolean contains(TValue[] values, BiFunction<TValue, Integer, Boolean> validator) {
        validate(values);

        for(var i = 0; i < values.length; i++) {
            if (validator.apply(values[i], i)) {
                return true;
            }
        }

        return false;
    }

    public static Character[] toCharArray(String value) {
        var length = value.length();
        var newCharArray = new Character[value.length()];

        for(var i = 0; i < length; i++) {
            newCharArray[i] = value.charAt(i);
        }

        return newCharArray;
    }

    private static <TValue> void validate(TValue[] values) {
        if(values == null) {
            throw new IllegalArgumentException("Invalid array argument");
        }

        if(values.length == 0) {
            return;
        }

        if(Arrays.stream(values).allMatch(value -> value == null)) {
            throw new NullPointerException("Null value in sequence");
        }
    }
}
