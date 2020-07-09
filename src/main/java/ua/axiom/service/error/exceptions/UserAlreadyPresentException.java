package ua.axiom.service.error.exceptions;

import ua.axiom.service.error.LightVerboseException;

public class UserAlreadyPresentException extends LightVerboseException {

    public UserAlreadyPresentException() {
    }

    @Override
    public String getMessageName() {
        return "errormsg.user-already-present-msg";
    }
}
