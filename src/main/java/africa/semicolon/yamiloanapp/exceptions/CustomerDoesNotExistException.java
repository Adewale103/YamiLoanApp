package africa.semicolon.yamiloanapp.exceptions;

public class CustomerDoesNotExistException extends RuntimeException {
    private int statusCode;
    public CustomerDoesNotExistException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
