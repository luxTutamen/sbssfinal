package ua.axiom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.service.LocalisationService;

import java.util.Locale;
import java.util.Map;

@Controller
public class MainPageController {

    private final static Locale LOCALE_DEFAULT = new Locale("UA");

    //  private UserRepository userRepository;
    @Autowired
    private LocalisationService localisationService;

//    @Autowired
//    public MainPageController(UserRepository userRepository, LocalisationService localisationService) {
//        this.userRepository = userRepository;
//        this.localisationService = localisationService;
//    }

    @RequestMapping("/")
    public ModelAndView getMainPage(Map<String, Object> model) {
        localisationService.setLocalisedMessages(
                model,
                LOCALE_DEFAULT,
                "sentence.already-logged",
                "sentence.login-appeal",
                "word.password",
                "word.register",
                "word.submit",
                "word.login",
                "word.or"
        );

        return new ModelAndView("index", model);
    }

    /*
    @PostMapping(params="register")
    public String getRegisterMapping() {
        System.out.println("reg mapping");
        return "reg";
    }

    @PostMapping(params="login")
    public String getLoginMapping(
            @RequestParam(name = "login") String user,
            @RequestParam(name = "password") String password
    ) {
        System.out.println("login mapping " + user + ":" + password);
        return "/userpage";
    }


     */

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


}
