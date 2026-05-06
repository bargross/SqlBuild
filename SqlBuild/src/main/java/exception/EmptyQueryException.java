package exception;

public class EmptyQueryException extends Exception {
    public EmptyQueryException() {
        super("Query is empty, ensure your query is built before generating a parameterized query is generated");
    }

    public EmptyQueryException(String errorMessage) {
        super(errorMessage);
    }
}
