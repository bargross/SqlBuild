package exception;


public class GenericCastException extends Exception {
    public GenericCastException(String errorMessage) {
        super(errorMessage);
    }

    public GenericCastException(String errorMessage, String... values) {
        super(String.format(errorMessage, (Object) values));
    }
}
