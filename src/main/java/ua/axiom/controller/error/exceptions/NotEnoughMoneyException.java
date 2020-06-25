package ua.axiom.controller.error.exceptions;

public class NotEnoughMoneyException extends LightVerboseException {
    public NotEnoughMoneyException() {
        super("You don't have enough funds to preform this action");
    }
}
