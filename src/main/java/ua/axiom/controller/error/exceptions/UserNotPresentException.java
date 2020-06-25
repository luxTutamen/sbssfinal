package ua.axiom.controller.error.exceptions;

public class UserNotPresentException extends LightVerboseException {

    public UserNotPresentException(String username) {
        super("User with username <" + username + "> not found ");
    }
}
