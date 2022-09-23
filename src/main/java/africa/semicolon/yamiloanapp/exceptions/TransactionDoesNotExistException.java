package africa.semicolon.yamiloanapp.exceptions;

public class TransactionDoesNotExistException extends RuntimeException{
    private int statusCode;
    public TransactionDoesNotExistException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
