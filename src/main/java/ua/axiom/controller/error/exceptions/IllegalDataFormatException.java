package ua.axiom.controller.error.exceptions;

public class IllegalDataFormatException extends LightVerboseException {
    public IllegalDataFormatException() {
        super("Illegal input format");
    }
}
