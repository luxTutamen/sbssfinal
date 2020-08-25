package ua.axiom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.axiom.model.User;
import ua.axiom.model.UserLocale;

import java.util.Map;

@Service
public class GuiService {
    private LocalisationService localisationService;

    @Autowired
    public GuiService(LocalisationService localisationService) {
        this.localisationService = localisationService;
    }

    /*public void populateModelWithNavbarData(Map<String, Object> model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        localisationService.setLocalisedMessages(
                model,
                user.getLocale(),
                "word.submit",
                "word.logout",
                "sentence.logged-welcome",
                "sentence.logged-as",
                "word.logout",
                "word.company-name"
        );

        model.put("username", user.getUsername());
        model.put("current-locale", user.getLocale());
        model.put("locales", UserLocale.values());
    }*/
}
