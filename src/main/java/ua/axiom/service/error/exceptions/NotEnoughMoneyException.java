package ua.axiom.service.error.exceptions;

import ua.axiom.service.error.LightVerboseException;

public class NotEnoughMoneyException extends LightVerboseException {

    @Override
    public String getViewMessageName() {
        return "errormsg.not-enough-money-msg";
    }
}
