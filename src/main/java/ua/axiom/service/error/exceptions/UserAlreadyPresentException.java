package ua.axiom.service.error.exceptions;

import ua.axiom.service.error.LightVerboseException;

public class UserAlreadyPresentException extends LightVerboseException {

    @Override
    public String getViewMessageName() {
        return "errormsg.user-already-present-msg";
    }
}
