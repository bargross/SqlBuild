package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class Mapper {
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

        for(var value: values) {
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

    public static <TValue> List<TValue> toList(Stream<TValue> stream) {
        if (stream == null) {
            throw new NullPointerException("Stream is null");
        }

        var length = (int)stream.count();
        if (length == 0) {
            throw new IllegalArgumentException("Stream is Empty");
        }

        var container = new ArrayList<TValue>(length);

        stream.forEachOrdered(container::add);

        return container;
    }

    public static <TValue> TValue[] toArray(List<TValue> values) {
        var container = (TValue[])new Object[values.size()];

        var size = values.size();
        for(var i = 0; i < size; ++i) {
            container[i] = values.get(i);
        }

        return container;
    }
}
