package ua.axiom.service.error.exceptions;

import ua.axiom.service.error.LightVerboseException;

public class WrongFormatException extends LightVerboseException {

    @Override
    public String getMessageName() {
        return "errormsg.wrong-input-format";
    }
}
