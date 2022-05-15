package util;

public interface IValidator<TValue extends Object> {
    boolean validate(TValue value);
}
