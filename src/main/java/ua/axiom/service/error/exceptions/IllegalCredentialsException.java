package ua.axiom.service.error.exceptions;

import ua.axiom.service.error.LightVerboseException;

public class IllegalCredentialsException extends LightVerboseException {

    @Override
    public String getMessageName() {
        return "errormsg.illegal-credentials";
    }
}
