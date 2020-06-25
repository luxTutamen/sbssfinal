package ua.axiom.controller.error.exceptions;

public class IllegalCredentialsException extends LightVerboseException {
    public IllegalCredentialsException() {
        super("Wrong credentials");
    }
}
