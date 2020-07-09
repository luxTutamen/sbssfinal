package ua.axiom.service.error.exceptions;

import ua.axiom.service.error.LightVerboseException;
import ua.axiom.service.LocalisationService;

public class IllegalDataFormatException extends LightVerboseException {

    public IllegalDataFormatException(LocalisationService localisationService) {
        super(localisationService);
    }

    @Override
    public String getMessageName() {
        return "errormsg.illegal-input-format";
    }
}
