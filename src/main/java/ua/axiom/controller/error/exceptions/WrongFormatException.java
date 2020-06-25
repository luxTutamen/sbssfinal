package ua.axiom.controller.error.exceptions;

public class WrongFormatException extends LightVerboseException {
    public WrongFormatException(String fieldName) {
        super( "Field <" + fieldName + "> is of wrong format");
    }
}
