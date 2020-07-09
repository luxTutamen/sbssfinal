package ua.axiom.service.error.exceptions;

import ua.axiom.service.error.LightVerboseException;

public class UserNotPresentException extends LightVerboseException {

    public UserNotPresentException() {
    }

    @Override
    public String getMessageName() {
        return "errormsg.user-not-present";
    }
}
