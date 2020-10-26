package ua.axiom.service.error.exceptions;

import ua.axiom.service.error.LightVerboseException;

public class GeneralRegisterException extends LightVerboseException {

    @Override
    public String getViewMessageName() {
        return "errormsg.general-register-exception";
    }
}
