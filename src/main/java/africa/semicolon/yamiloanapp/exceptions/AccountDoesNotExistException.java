package africa.semicolon.yamiloanapp.exceptions;

public class AccountDoesNotExistException extends RuntimeException {
    private int statusCode;
    public AccountDoesNotExistException(String message, int statusCode ) {
        super(message);
        this.statusCode = statusCode;
    }
}
