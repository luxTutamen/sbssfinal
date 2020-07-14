package ua.axiom.service.error.exceptions;

import ua.axiom.service.error.LightVerboseException;

public class UserAlreadyPresentException extends LightVerboseException {

    @Override
    public String getMessageName() {
        return "errormsg.user-already-present-msg";
    }
}
