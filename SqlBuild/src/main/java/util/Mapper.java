package util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class Mapper {
    public static <TValue extends Object, TResult extends Object> TResult[] map(TValue[] values, Function<TValue, TResult> predicate) {
        var container = (TResult[])new Object[values.length];

        for(var i = 0; i < values.length; ++i) {
            var temp = predicate.apply(values[i]);

            container[i] = temp;
        }

        return container;
    }

    public static <TValue extends Object, TResult extends Object> List<TResult> map(List<TValue> values, Function<TValue, TResult> predicate) {
        var container = new ArrayList<TResult>(values.size());

        for(var value: values) {
            var mappedValue = predicate.apply(value);

            container.add(mappedValue);
        }

        return container;
    }


    public static <TValue extends Object, TResult extends Object> TResult[] mapToArray(List<TValue> values, Function<TValue, TResult> predicate) {
        var resultList = map(values, predicate);

        return toArray(resultList);
    }

    public static <TValue> TValue[] filter(TValue[] values, Function<TValue, Boolean> validator) {
        var filtered = new ArrayList<TValue>(values.length/2);

        for(var i = 0; i < values.length; ++i) {
            var value = values[i];
            if(validator.apply(value)) {
                filtered.add(value);
            }
        }

        return toArray(filtered);
    }

    public static <TValue> List<TValue> toList(TValue[] values) {
        var container = new ArrayList<TValue>(values.length);

        for(var value: values) {
            container.add(value);
        }

        return container;
    }

    public static <TValue> List<TValue> toList(Stream<TValue> stream) {
        if (stream == null) {
            throw new NullPointerException("Stream is null");
        }

        if (stream.count() == 0) {
            throw new IllegalArgumentException("Stream is Empty");
        }

        var length = (int)stream.count();
        var container = new ArrayList<TValue>(length);

        stream.forEach(v -> container.add(v));

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
