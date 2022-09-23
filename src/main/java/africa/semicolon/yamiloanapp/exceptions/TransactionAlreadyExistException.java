package africa.semicolon.yamiloanapp.exceptions;

public class TransactionAlreadyExistException extends RuntimeException {
    private int statusCode;
    public TransactionAlreadyExistException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
