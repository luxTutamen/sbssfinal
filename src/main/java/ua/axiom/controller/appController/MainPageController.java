package ua.axiom.controller.appController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.MultiViewController;
import ua.axiom.model.objects.Role;
import ua.axiom.model.objects.User;
import ua.axiom.model.objects.UserLocale;
import ua.axiom.repository.UserRepository;
import ua.axiom.service.LocalisationService;

import java.util.*;
import java.util.function.Function;

@Controller
public class MainPageController extends MultiViewController {
    //  todo remove
    private static final Locale LOCALE_DEFAULT = new Locale("UA");

    private UserRepository userRepository;
    private LocalisationService localisationService;

    @Autowired
    public MainPageController(UserRepository userRepository, LocalisationService localisationService) {
        this.userRepository = userRepository;
        this.localisationService = localisationService;

        addController(new HashSet<>(Arrays.asList(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))), new AnonymousMainPageController());
        addController(new HashSet<>(Arrays.asList(Role.DRIVER, Role.ADMIN, Role.CLIENT)), new AuthorisedMainPageController());
    }

    @RequestMapping("/")
    public ModelAndView mapRequest() {
        return super.getRequestMapping(new ModelAndView("/"));
    }

    private class AnonymousMainPageController implements Function<ModelAndView, ModelAndView> {

        @Override
        public ModelAndView apply(ModelAndView modelAndView) {
            Map<String, Object> model = modelAndView.getModel();
            model.putAll(fillContent());

            return new ModelAndView("index/anonymous", model);
        }

        //  todo make singletone
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

    private class AuthorisedMainPageController implements Function<ModelAndView, ModelAndView> {
        @Override
        public ModelAndView apply(ModelAndView modelAndView) {
            Map<String, Object> model = modelAndView.getModel();
            User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            model.put("info.username", user.getUsername());
            model.put("locales", UserLocale.getLocalesList());
            model.put("current-locale", user.getLocale());

            localisationService.setLocalisedMessages(
                    model,
                    user.getLocale().toJavaLocale(),
                    "sentence.to-app-button",
                    "sentence.login-page-desc"
            );

            fillHeader(model, user);

            return new ModelAndView("index/logged", model);
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
                .getRequestMapping(new ModelAndView("/"), new SimpleGrantedAuthority("ROLE_ANONYMOUS"))
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

    /*
        @PostMapping(value = "/", params = "login")
        public String mainPagePost(
                @RequestParam String login,
                @RequestParam String password,
                @RequestParam(required = false, defaultValue = "eng") String lang,
                Map<String, Object> model) {

            System.out.println(login + " " + password + " " + lang);
            model.put("timestamp", new Timestamp(1111L));

            return "application";

            User user;
            try {
                user = userRepository.findByUsername(login).get();
            } catch (NoSuchElementException nsee) {
                System.out.println("errr lol");
                return "error";
            }

            if(user.getRole() == Role.USER) {
                return "app";
            } else if(user.getRole() == Role.ADMIN) {
                return "admin";
            } else if(user.getRole() == Role.DRIVER) {
                return "cabinet";
            } else {
                return "error";
            }
        }
    */
    /*
    private String getLoginLoginPage(Map<String, Object> model,
                                     @RequestParam(value = "error", required = false) String error,
                                     @RequestParam(value = "logout", required = false) String logout) {
        Locale locale = new Locale("UA");

        model.put("logout-alert", error != null);
        model.put("register-alert", logout != null);

        model.put("selected_language", localisationService.getLocalisedMessage("word.language_name", locale));
        model.put("or-word", localisationService.getLocalisedMessage("word.or", locale));
        model.put("login-appeal", localisationService.getLocalisedMessage("sentence.login-appeal", locale));
        model.put("password-word", localisationService.getLocalisedMessage("word.password", locale));
        model.put("register-word", localisationService.getLocalisedMessage("word.register", locale));
        model.put("submit-word", localisationService.getLocalisedMessage("word.submit", locale));
        model.put("login-word", localisationService.getLocalisedMessage("word.login", locale));

        return "index";
    }

    private String getUserLoginPage(Map<String, Object> model) {
        //  User o = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Locale locale = new Locale("UA");

        model.put("show-login", true);
        model.put("already-login-msg", localisationService.getLocalisedMessage("sentences.already-login-msg", locale));
        model.put("username", "dummy");
        model.put("user-role", "dummy");

        return "index";
    }

     */


}
