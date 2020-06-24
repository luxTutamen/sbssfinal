package ua.axiom.controller.appController.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.MustacheController;
import ua.axiom.controller.error.exceptions.IllegalCredentialsException;
import ua.axiom.model.User;
import ua.axiom.model.UserLocale;
import ua.axiom.service.LocalisationService;

import java.util.HashMap;
import java.util.Map;

@Component
@ControllerAdvice
public class AnonymousMainPageController extends MustacheController<User> {
    private LocalisationService localisationService;

    @Autowired
    public AnonymousMainPageController(LocalisationService localisationService) {
        this.localisationService = localisationService;
    }

    @Override
    protected ModelAndView formResponse(Map<String, Object> model) {
        return new ModelAndView("index/anonymous", model);
    }

    @Override
    public void processRequest(User user) {}

    @Override
    protected User getPersistedUser() {
        return null;
    }

    @Override
    protected void fillUserSpecificData(Map<String, Object> model, User user) {}

    @Override
    protected void fillLocalisedPageData(Map<String, Object> model, UserLocale user) {
        localisationService.setLocalisedMessages(
                model,
                UserLocale.DEFAULT_LOCALE,
                "sentence.already-logged",
                "sentence.login-appeal",
                "word.password",
                "word.register",
                "word.submit",
                "word.error",
                "sentence.wrong-credential-msg",
                "word.login",
                "word.or",
                "sentence.login-page-desc",
                "sentence.confidentiality-promise"
        );
    }

    @ExceptionHandler(IllegalCredentialsException.class)
    public ModelAndView exception() {
        Map<String, Object> model = new HashMap<>();
        model.put("error", localisationService.getLocalisedMessage( "sentence.wrong-credentials", UserLocale.DEFAULT_LOCALE));
        return super.serveRequest(model);
    }
}
