package ua.axiom.service.error.exceptions;

import ua.axiom.service.error.LightVerboseException;

public class JustTakenException extends LightVerboseException {

    @Override
    public String getViewMessageName() {
        return "errormsg.order-was-taken";
    }
}
