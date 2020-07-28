package ua.axiom.controller.appController.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.ThymeleafController;
import ua.axiom.model.User;
import ua.axiom.model.UserLocale;
import ua.axiom.service.LocalisationService;
import ua.axiom.service.error.exceptions.IllegalCredentialsException;
import ua.axiom.service.error.exceptions.IllegalDataFormatException;

@Component
@ControllerAdvice
public class AnonymousMainPageController extends ThymeleafController<User> {
    private LocalisationService localisationService;

    @Autowired
    public AnonymousMainPageController(LocalisationService localisationService) {
        this.localisationService = localisationService;
    }

    @Override
    protected ModelAndView formResponse(Model model) {
        return new ModelAndView("index/anonymous", model.asMap());
    }

    @Override
    protected void fillUserSpecificData(Model model, User user) { }

    @Override
    public void processRequest(User user) {}

    @Override
    protected User getPersistedUser() {
        return null;
    }

    /*@ExceptionHandler(IllegalDataFormatException.class)
    public ModelAndView exception(Model model) {
        model.addAttribute("error", localisationService.getLocalisedMessage( "sentence.wrong-credentials", UserLocale.DEFAULT_LOCALE));
        return super.serveRequest(model);
    }

    @ExceptionHandler({InternalAuthenticationServiceException.class, IllegalCredentialsException.class})
    public ModelAndView authExceptionController(Model model) {
        model.addAttribute("error", "wrong credentials");
        return super.serveRequest(model);
    }*/

}
