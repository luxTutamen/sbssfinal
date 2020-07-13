package ua.axiom.service.error;

import ua.axiom.model.UserLocale;
import ua.axiom.service.LocalisationService;

import java.io.IOException;

public abstract class LightVerboseException extends IOException {

    public abstract String getMessageName();

    public String getShortMessage(LocalisationService localisationService) {
        return localisationService.getLocalisedMessage(
                getMessageName(),
                UserLocale.getContextLocale());
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}
