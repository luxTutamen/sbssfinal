package ua.axiom.controller.exceptions;

public class JustTakenException extends Exception {
    @Override
    public String getMessage() {
        return "This order was just taken by someone else!" + super.getMessage();
    }
}
