package ua.axiom.controller.appController.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.MustacheController;
import ua.axiom.model.User;
import ua.axiom.model.UserLocale;
import ua.axiom.service.LocalisationService;

import java.util.Map;

@Service
public class AuthorisedMainPageController extends MustacheController<User> {
    private LocalisationService localisationService;

    @Autowired
    public AuthorisedMainPageController(LocalisationService localisationService) {
        this.localisationService = localisationService;
    }

    @Override
    protected ModelAndView formResponse(Map<String, Object> model) {
        return new ModelAndView("index/logged", model);
    }

    @Override
    public void processRequest(User user) { }

    @Override
    protected void fillUserSpecificData(Map<String, Object> model, User user) {
        model.put("info.username", user.getUsername());

        localisationService.setLocalisedMessages(
                model,
                user.getLocale(),
                "sentence.to-app-button",
                "sentence.login-page-desc"
        );
    }

    @Override
    protected void fillLocalisedPageData(Map<String, Object> model, UserLocale locale) {
        localisationService.setLocalisedMessages(
                model,
                locale,
                "word.logout",
                "sentence.logged-welcome",
                "sentence.logged-as",
                "word.logout",
                "word.company-name"
        );

    }
}