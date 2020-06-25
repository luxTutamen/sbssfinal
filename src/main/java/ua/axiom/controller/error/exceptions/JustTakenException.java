package ua.axiom.controller.error.exceptions;

public class JustTakenException extends LightVerboseException {
    public JustTakenException() {
        super("This order was just taken by someone else!");
    }
}
