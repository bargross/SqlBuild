package util.mapper;

import exception.StreamAlreadyConsumedException;
import util.guard.StreamGuard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class Mapper {

    @SuppressWarnings("unchecked")
    public static <TValue, TResult> TResult[] map(TValue[] values, Function<TValue, TResult> predicate) {
        var container = (TResult[]) new Object[values.length];

        for (var i = 0; i < values.length; ++i) {
            var temp = predicate.apply(values[i]);

            container[i] = temp;
        }

        return container;
    }

    public static <TValue, TResult> List<TResult> map(List<TValue> values, Function<TValue, TResult> predicate) {
        var container = new ArrayList<TResult>(values.size());

        for (var value: values) {
            var mappedValue = predicate.apply(value);

            container.add(mappedValue);
        }

        return container;
    }


    public static <TValue, TResult> TResult[] mapToArray(List<TValue> values, Function<TValue, TResult> predicate) {
        var resultList = map(values, predicate);

        return toArray(resultList);
    }

    public static <TValue> TValue[] filter(TValue[] values, Function<TValue, Boolean> validator) {
        var filtered = new ArrayList<TValue>(values.length/2);

        for (TValue value : values) {
            if (validator.apply(value)) {
                filtered.add(value);
            }
        }

        return toArray(filtered);
    }

    public static <TValue> List<TValue> toList(TValue[] values) {
        var container = new ArrayList<TValue>(values.length);

        Collections.addAll(container, values);

        return container;
    }

    public static <TValue> List<TValue> toList(Stream<TValue> stream)
            throws NullPointerException, IllegalArgumentException, StreamAlreadyConsumedException {
        if (stream == null) {
            throw new NullPointerException("Stream is null");
        }

        var length = (int)stream.count();
        if (length == 0) {
            throw new IllegalArgumentException("Stream is Empty");
        }

        var container = new ArrayList<TValue>(length);

        var assuranceContainer = StreamGuard.isConsumed(stream);

        assuranceContainer.ifPresent(stm -> stm.forEachOrdered(container::add));

        if (container.isEmpty()) {
            throw new StreamAlreadyConsumedException("Stream has already been consumed.");
        }

        return container;
    }

    public static char[] toCharArray(String value) {
        var length = value.length();
        var characters = new char[value.length()];

        for (int i = 0; i < length; ++i) {
            characters[i] = value.charAt(i);
        }

        return characters;
    }

    public static Character[] toCharacterArray(String value) {
        var length = value.length();
        var characters = new Character[value.length()];

        for (int i = 0; i < length; ++i) {
            characters[i] = value.charAt(i);
        }

        return characters;
    }

    @SuppressWarnings("unchecked")
    public static <TValue> TValue[] toArray(List<TValue> values) {
        var container = (TValue[])new Object[values.size()];

        var size = values.size();
        for(var i = 0; i < size; ++i) {
            container[i] = values.get(i);
        }

        return container;
    }
}
