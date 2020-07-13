package ua.axiom.service.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axiom.model.UserLocale;
import ua.axiom.service.LocalisationService;

import java.util.function.Supplier;

/*
@Service
public class LightVerboseExceptionFactory {
    private LocalisationService localisationService;

    @Autowired
    public LightVerboseExceptionFactory(LocalisationService localisationService) {
        this.localisationService = localisationService;
    }

    public <T extends LightVerboseException> T createLocalisedException(Supplier<T> exceptionSupplier, String messqge, UserLocale locale) {
        T exception = exceptionSupplier.get();
        exception.setShortMessage(localisationService.getLocalisedMessage(messqge, locale));

        return exception;
    }
}
*/
