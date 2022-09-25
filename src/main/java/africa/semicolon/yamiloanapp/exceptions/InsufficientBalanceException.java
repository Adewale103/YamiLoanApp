package africa.semicolon.yamiloanapp.exceptions;

public class InsufficientBalanceException extends RuntimeException {
    private int statusCode;
    public InsufficientBalanceException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
