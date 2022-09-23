package africa.semicolon.yamiloanapp.exceptions;

public class CustomerAlreadyExistException extends RuntimeException {
    private int statusCode;
    public CustomerAlreadyExistException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
