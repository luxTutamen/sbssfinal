package ua.axiom.controller.apiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.model.objects.Client;
import ua.axiom.model.objects.Order;
import ua.axiom.model.objects.User;
import ua.axiom.service.LocalisationService;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/api/neworder")
public class NewOrderController {
    private LocalisationService localisationService;

    @Autowired
    public NewOrderController(LocalisationService localisationService) {
        this.localisationService = localisationService;
    }

    @RequestMapping
    public ModelAndView orderRequest() {
        Map<String, Object> model = new HashMap<>();
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.put("new-order-details", Order.getOrderInputDescriptions());

        fillLocalisedText(model, client.getLocale().toJavaLocale());
        fillHeader(model, client);

        return new ModelAndView("/apiPages/neworder", model);

    }

    private void fillHeader(Map<String, Object> model, User user) {
        localisationService.setLocalisedMessages(
                model,
                user.getLocale().toJavaLocale(),
                "word.logout",
                "sentence.logged-welcome",
                "sentence.logged-as",
                "word.logout",
                "word.company-name"
        );

        model.put("username", user.getUsername());
        model.put("current-locale", user.getLocale());
    }

    private void fillLocalisedText(Map<String, Object> model, Locale userLocale) {
        localisationService.setLocalisedMessages(
                model,
                userLocale,
                "sentence.new-order-page-desc",
                "sentence.new-order-request-msg",
                "word.submit"

        );

    }
}
