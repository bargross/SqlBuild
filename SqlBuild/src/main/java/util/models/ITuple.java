package util.models;

public interface ITuple<TItemOne, TItemTwo> {
    TItemOne getItemOne();
    TItemTwo getItemTwo();
    GenericValue getNonNull();
    boolean equals(Object o);
}
