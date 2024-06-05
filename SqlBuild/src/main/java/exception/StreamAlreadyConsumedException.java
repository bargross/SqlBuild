package exception;

public class StreamAlreadyConsumedException extends Exception {
    public StreamAlreadyConsumedException(String errorMessage, String... values)  {
        super(String.format(errorMessage, (Object) values));
    }
}
