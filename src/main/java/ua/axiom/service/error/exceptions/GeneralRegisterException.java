package ua.axiom.service.error.exceptions;

import ua.axiom.service.error.LightVerboseException;

public class GeneralRegisterException extends LightVerboseException {
    @Override
    public String getMessageName() {
        return "errormsg.ua.axiom.testing.general-register-exception";
    }
}
