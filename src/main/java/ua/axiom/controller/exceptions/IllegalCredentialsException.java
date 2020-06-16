package ua.axiom.controller.exceptions;

public class IllegalCredentialsException extends LightException {

    private String displayMessage;

    public IllegalCredentialsException(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }
}
