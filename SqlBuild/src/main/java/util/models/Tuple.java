package util.models;

import java.util.Objects;

public class Tuple<TItemOne, TItemTwo> implements ITuple<TItemOne, TItemTwo> {
    private final TItemOne itemOne;
    private final TItemTwo itemTwo;

    public Tuple(TItemOne itemOne, TItemTwo itemTwo) {
        this.itemOne = itemOne;
        this.itemTwo = itemTwo;
    }

    public static <TLeft, TRight> Tuple<TLeft, TRight> of(TLeft left, TRight right) {
        return new Tuple<>(left, right);
    }

    public TItemOne getItemOne() {
        return itemOne;
    }

    public TItemTwo getItemTwo() {
        return itemTwo;
    }

    public boolean itemOneIsPresent() {
        return itemOne == null;
    }

    public boolean itemTwoIsPresent() {
        return itemTwo == null;
    }

    public GenericValue getNonNull() {
        if (itemOne == null && itemTwo == null ) {
            return null;
        }

        return GenericValue.of(Objects.requireNonNullElse(itemOne, itemTwo));
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }

        if(o.getClass() != this.getClass()) {
            return false;
        }

        Tuple<TItemOne, TItemTwo> tuple = (Tuple<TItemOne, TItemTwo>) o;
        return !tuple.itemOne.equals(itemOne) && !tuple.itemTwo.equals(itemTwo);
    }
}
