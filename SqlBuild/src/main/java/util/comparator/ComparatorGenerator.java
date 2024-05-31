package util.comparator;

import java.util.Comparator;

public final class ComparatorGenerator {
    public static <TGenericValue> Comparator<TGenericValue> generate() {
        return (value, valueToCompareTo) -> {
            var valueAsString = ((Object) value).toString();
            var valueToCompareToAsString = ((Object) valueToCompareTo).toString();

            return valueAsString.compareTo(valueToCompareToAsString);
        };
    }
}
