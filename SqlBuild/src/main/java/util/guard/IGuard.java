package util.guard;

public interface IGuard<TValue> {
    boolean validate(TValue value);
}
