package ua.axiom.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.model.objects.User;
import ua.axiom.model.objects.UserLocale;

import java.util.Map;

/**
 * Abstract representation of a mustache VC, contains major events in a dynamic webpage creation
 * Only works with logged in users
 * @param <T>
 */
public abstract class MustacheController <T extends User> {
    /**
     * Return new ModelAndView from here with needed template file and given model
     * @param model constructed model, populated with localised GUI text, and user data
     * @return ModelAndView to go for
     */
    protected abstract ModelAndView formResponse(Map<String, Object> model);

    /**
     * Incapsulates specific on-request action (like check for bonuses, request count, etc)
     * @param user that is requesting the page
     */
    public abstract void processRequest(T user);

    /**
     * Gets current logged id from a context and uses it to get user from a database
     * @return USer object from a database
     */
    protected abstract T getPersistedUser();

    /**
     * Populates model with data, that needs USer object to be calculated (like locale, username, etc)
     * @param model model to put data into
     * @param user
     */
    protected abstract void fillUserSpecificData(Map<String, Object> model, T user);

    /**
     * Populates model with template-specific, localised date (button text, etc)
     * @param model model to put data into
     * @param locale
     */
    protected abstract void fillLocalisedPageData(Map<String, Object> model, UserLocale locale);

    /**
     * processes request.  Containing class must be annotated with @RequestMapping with specific URI!
     * @param model to put data into
     * @return ModelAndView with model, filled with localised data, and View as a specific Mustache template path
     */
    @RequestMapping
    public final ModelAndView serveRequest(Map<String, Object> model) {
        T user = getPersistedUser();
        fillLocalisedPageData(model, user.getLocale());
        fillUserSpecificData(model, user);
        processRequest(user);

        return formResponse(model);
    }
}
