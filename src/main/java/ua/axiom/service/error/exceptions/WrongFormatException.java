package ua.axiom.service.error.exceptions;

import ua.axiom.service.error.LightVerboseException;

public class WrongFormatException extends LightVerboseException {

    @Override
    public String getViewMessageName() {
        return "errormsg.wrong-input-format";
    }
}
