package ua.axiom.service.error;

import org.springframework.context.i18n.LocaleContextHolder;
import ua.axiom.model.UserLocale;
import ua.axiom.service.LocalisationService;

import java.io.IOException;

public abstract class LightVerboseException extends IOException {

    private String localisedErrorMSG;

    public LightVerboseException(LocalisationService localisationService) {
        this.localisedErrorMSG = localisationService.getLocalisedMessage(
                getMessageName(),
                UserLocale.toUserLocale(LocaleContextHolder.getLocale()));
    }

    public LightVerboseException() { }

    public LightVerboseException(String localisedErrorMSG) {
        this.localisedErrorMSG = localisedErrorMSG;
    }

    public abstract String getMessageName();

    public String getShortMessage() {
        return localisedErrorMSG;
    }

    public void setShortMessage(String localisedErrorMSG) {
        this.localisedErrorMSG = localisedErrorMSG;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}
