package ua.axiom.service.error.exceptions;

import ua.axiom.service.error.LightVerboseException;

public class JustTakenException extends LightVerboseException {

    public JustTakenException() {
        super();
    }

    @Override
    public String getMessageName() {
        return "errormsg.order-was-taken";
    }

}
