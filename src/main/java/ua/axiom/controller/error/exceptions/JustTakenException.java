package ua.axiom.controller.error.exceptions;

public class JustTakenException extends LightException {
    @Override
    public String getMessage() {
        return "This order was just taken by someone else!" + super.getMessage();
    }
}
