package ssvv.example.validation;

public class ValidationException extends RuntimeException{
    public ValidationException(String exception) {
        super(exception);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

