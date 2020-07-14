package ua.axiom.service.error.exceptions;

import ua.axiom.service.error.LightVerboseException;

public class UserNotPresentException extends LightVerboseException {

    @Override
    public String getMessageName() {
        return "errormsg.user-not-present";
    }
}
