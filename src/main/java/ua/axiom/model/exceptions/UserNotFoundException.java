package ua.axiom.model.exceptions;

public class UserNotFoundException extends Exception{
    private String credentials;

    public UserNotFoundException(String credentials) {
        this.credentials = credentials;
    }

    @Override
    public String getMessage() {
        return "user with credentials "
                + credentials
                + " not found"
                + super.getMessage();
    }
}
