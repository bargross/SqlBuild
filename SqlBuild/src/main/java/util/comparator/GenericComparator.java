package util.comparator;

import java.util.Comparator;

public class GenericComparator<TValue extends Object> {
    public Comparator<TValue> comparator;

    public GenericComparator() {
        comparator = ComparatorGenerator.<TValue>generate();
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
        if(value == null) {
            throw new IllegalArgumentException("Invalid value");
        }

        if(value == null) {
            throw new IllegalArgumentException("Invalid value to compare to");
        }

        switch(operator) {
            case EQUAL: return comparator.compare(value, valueToCompareTo) == 0;
            case LESSTHAN: return comparator.compare(value, valueToCompareTo) < 0;
            case GREATERTHAN: return comparator.compare(value, valueToCompareTo) > 0;
            default: return false;
        }
    }
}
