package africa.semicolon.yamiloanapp.exceptions;

public class LoanException extends RuntimeException{
    private int statusCode;
    public LoanException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
