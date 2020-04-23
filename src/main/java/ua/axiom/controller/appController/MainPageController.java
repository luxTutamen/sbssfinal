package ua.axiom.controller.appController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.MultiViewController;
import ua.axiom.model.objects.User;
import ua.axiom.model.objects.UserLocale;
import ua.axiom.repository.UserRepository;
import ua.axiom.service.LocalisationService;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

@Controller
@RequestMapping("/")
public class MainPageController extends MultiViewController {
    //  todo remove
    private static final Locale LOCALE_DEFAULT = new Locale("UA");

    private static final Supplier<Boolean> anonymousViewPredicate =
            () -> SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getAuthorities()
                        .stream()
                        .anyMatch(a -> a.toString().equals("ROLE_ANONYMOUS"));

    private static final Supplier<Boolean> loggedViewPredicates =
            () -> SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getAuthorities()
                        .stream()
                        .anyMatch(a -> { return a.toString().equals("DRIVER")
                                                || a.toString().equals("CLIENT")
                                                || a.toString().equals("ADMIN"); });

    private UserRepository userRepository;
    private LocalisationService localisationService;

    @Autowired
    public MainPageController(UserRepository userRepository, LocalisationService localisationService) {
        this.userRepository = userRepository;
        this.localisationService = localisationService;

        addController(anonymousViewPredicate, new AnonymousMainPageController());
        addController(loggedViewPredicates, new AuthorisedMainPageController());
    }

    @RequestMapping
    public ModelAndView mapRequest() {
        return super.getRequestMapping(new ConcurrentModel());
    }

    private class AnonymousMainPageController implements Function<Model, ModelAndView> {
        @Override
        public ModelAndView apply(Model model) {

            model.addAllAttributes(fillContent());

            return new ModelAndView("index/anonymous", model.asMap());
        }

        private Map<String, Object> fillContent() {
            Map<String, Object> model = new HashMap<>();

            localisationService.setLocalisedMessages(
                    model,
                    LOCALE_DEFAULT,
                    "sentence.already-logged",
                    "sentence.login-appeal",
                    "word.password",
                    "word.register",
                    "word.submit",
                    "word.login",
                    "word.or",
                    "sentence.login-page-desc",
                    "sentence.confidentiality-promise"
            );

            return model;
        }

    }

    private class AuthorisedMainPageController implements Function<Model, ModelAndView> {
        @Override
        public ModelAndView apply(Model model) {
            User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            model.addAttribute("info.username", user.getUsername());
            model.addAttribute("locales", UserLocale.getLocalesList());
            model.addAttribute("current-locale", user.getLocale());

            localisationService.setLocalisedMessages(
                    model.asMap(),
                    user.getLocale().toJavaLocale(),
                    "sentence.to-app-button",
                    "sentence.login-page-desc"
            );

            fillHeader(model.asMap(), user);

            return new ModelAndView("index/logged", model.asMap());
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
        }
    }


    @ExceptionHandler(NullPointerException.class)
    public ModelAndView exceptionHandler() {
        Map<String, Object> model = super
                .getRequestMapping(new ConcurrentModel())
                .getModel();

        model.put("error", true);

        localisationService.setLocalisedMessages(
                model,
                LOCALE_DEFAULT,
                "word.error",
                "sentence.wrong-credential-msg"
        );

        return new ModelAndView("index/anonymous", model);
    }

}
