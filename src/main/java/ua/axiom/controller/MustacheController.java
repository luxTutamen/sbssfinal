package ua.axiom.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.model.objects.User;
import ua.axiom.model.objects.UserLocale;

import java.util.Map;

/**
 *
 * @param <T>
 */
public abstract class MustacheController <T extends User> {
    protected abstract void fillUserSpecificData(Map<String, Object> model, T user);

    protected abstract void fillLocalisedPageData(Map<String, Object> model, UserLocale locale);

    protected abstract T getPersistedUser();

    protected abstract ModelAndView formResponse(Map<String, Object> model);

    public abstract void processRequest(T user);

    @RequestMapping
    public final ModelAndView serveRequest(Map<String, Object> model) {
        T user = getPersistedUser();
        fillLocalisedPageData(model, user.getLocale());
        fillUserSpecificData(model, user);
        processRequest(user);

        return formResponse(model);
    }
}
