package ua.axiom.controller.exceptions;

public class WrongFormatException extends LightException {
    private String fieldName;

    public WrongFormatException(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getMessage() {
        return "Field <" + fieldName + "> is of wrong format" + super.getMessage();
    }
}
