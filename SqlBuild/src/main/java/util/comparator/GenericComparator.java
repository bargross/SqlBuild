package util.comparator;

import java.util.Comparator;

public class GenericComparator<TValue> {
    public Comparator<TValue> comparator;

    public GenericComparator() {
        comparator = ComparatorGenerator.generate();
    }

    public boolean isGreater(TValue value, TValue valueToCompareTo) {
        return is(ComparatorOperators.GREATERTHAN, value, valueToCompareTo);
    }

    public boolean isEqual(TValue value, TValue valueToCompareTo) {
        return is(ComparatorOperators.EQUAL, value, valueToCompareTo);
    }

    public boolean isLessThan(TValue value, TValue valueToCompareTo) {
        return is(ComparatorOperators.LESSTHAN, value, valueToCompareTo);
    }

    private boolean is(ComparatorOperators operator, TValue value, TValue valueToCompareTo) {
        if (value == null) {
            throw new IllegalArgumentException("Invalid value");
        }

        if (valueToCompareTo == null) {
            throw new IllegalArgumentException("Invalid value to compare to");
        }

        return switch (operator) {
            case EQUAL -> comparator.compare(value, valueToCompareTo) == 0;
            case LESSTHAN -> comparator.compare(value, valueToCompareTo) < 0;
            case GREATERTHAN -> comparator.compare(value, valueToCompareTo) > 0;
            default -> false;
        };
    }
}
