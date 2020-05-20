package ua.axiom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.model.objects.User;
import ua.axiom.model.objects.UserLocale;
import ua.axiom.service.TEstService;
import ua.axiom.service.userpersistance.UserProvider;

import java.util.Map;
import java.util.function.Function;

/**
 * Abstract representation of a mustache VC, contains major events in a dynamic webpage creation
 * Only works with logged in users
 * @param <T>
 */
@Service
public abstract class MustacheController <T extends User> implements Function<Model, ModelAndView> {

    @Autowired
    private UserProvider<T> userProvider;

    @Autowired
    private TEstService tEstService;

    @Override
    public final ModelAndView apply(Model model) {
        return serveRequest(model.asMap());
    }

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
    public void processRequest(T user) {
        tEstService.invoke();
    }

    /**
     * Gets current logged id from a context and uses it to get user from a database
     * @return USer object from a database
     */
    protected T getPersistedUser() {
        return userProvider.getCurrentUserFromDB();
    }

    /**
     * Populates model with data, that needs USer object to be calculated (like locale, username, etc)
     * @param model model to put data into
     * @param user, may be null for non-authorised pages
     */
    protected abstract void fillUserSpecificData(Map<String, Object> model, @Nullable T user);

    //  todo remove nullable
    /**
     * Populates model with template-specific, localised date (button text, etc)
     * @param model model to put data into
     * @param userLocale
     */
    protected abstract void fillLocalisedPageData(Map<String, Object> model, UserLocale userLocale);

    /**
     * processes request.  Containing class must be annotated with @RequestMapping with specific URI!
     * @param model to put data into
     * @return ModelAndView with model, filled with localised data, and View as a specific Mustache template path
     */
    @RequestMapping
    public final ModelAndView serveRequest(Map<String, Object> model) {
        T user = getPersistedUser();
        fillLocalisedPageData(model, user != null ? user.getLocale() : null);
        fillUserSpecificData(model, user);
        processRequest(user);

        return formResponse(model);
    }
}
