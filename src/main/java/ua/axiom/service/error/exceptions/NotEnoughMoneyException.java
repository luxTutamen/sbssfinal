package ua.axiom.service.error.exceptions;

import ua.axiom.service.error.LightVerboseException;

public class NotEnoughMoneyException extends LightVerboseException {

    @Override
    public String getMessageName() {
        return "errormsg.not-enough-money-msg";
    }
}