package ua.axiom.service.error.exceptions;

import ua.axiom.service.error.LightVerboseException;

public class IllegalDataFormatException extends LightVerboseException {

    @Override
    public String getMessageName() {
        return "errormsg.illegal-input-format";
    }
}