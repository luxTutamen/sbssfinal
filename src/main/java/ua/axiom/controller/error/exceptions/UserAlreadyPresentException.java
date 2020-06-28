package ua.axiom.controller.error.exceptions;

public class UserAlreadyPresentException extends LightVerboseException {

    public UserAlreadyPresentException() {
        super("user with this username already present");
    }
}
