package ua.axiom.service.error.exceptions;

import ua.axiom.service.error.LightVerboseException;

public class FormInputException extends LightVerboseException {

    @Override
    public String getViewMessageName() {
        return "errormsg.form-input-exception";
    }
}
