package util.comparator;

import java.util.Comparator;

public final class ComparatorGenerator {
    public static <TGenericValue> Comparator<TGenericValue> generate() {
        return (value, valueToCompareTo) -> {
            var valueAsString = value.toString();
            var valueToCompareToAsString = valueToCompareTo.toString();

            return valueAsString.compareTo(valueToCompareToAsString);
        };
    }

    @SuppressWarnings("ComparatorResultComparison")
    public static <TGenericValue> boolean areEqual(TGenericValue original, TGenericValue valueToCompareTo) {
        return generate().compare(original, valueToCompareTo) == 1;
    }
}
