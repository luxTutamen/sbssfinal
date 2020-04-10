package ua.axiom.controller.exceptions;

public class UserNotPresentException extends Exception {
    private String username;

    public UserNotPresentException(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return "User with username <" + username + "> not found " + super.getMessage();
    }
}
