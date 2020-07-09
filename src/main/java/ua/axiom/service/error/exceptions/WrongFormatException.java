package ua.axiom.service.error.exceptions;

import ua.axiom.service.error.LightVerboseException;
import ua.axiom.service.LocalisationService;

public class WrongFormatException extends LightVerboseException {

    public WrongFormatException(LocalisationService localisationService) {
        super(localisationService);
    }

    @Override
    public String getMessageName() {
        return "errormsg.wrong-input-format";
    }
}
