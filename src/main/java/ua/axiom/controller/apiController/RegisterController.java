package ua.axiom.controller.apiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.axiom.controller.error.exceptions.IllegalCredentialsException;
import ua.axiom.model.User;
import ua.axiom.model.UserLocale;
import ua.axiom.repository.UserRepository;
import ua.axiom.service.LocalisationService;

import java.util.HashMap;
import java.util.Map;

//  todo refactor?
@Controller
@RequestMapping("/register")
public class RegisterController {

    private final String usernamePattern;
    private final String passwordPattern;

    @Autowired
    public RegisterController(
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            LocalisationService localisationService
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.localisationService = localisationService;

        usernamePattern = localisationService.getRegex("username", UserLocale.DEFAULT_LOCALE);
        passwordPattern = localisationService.getRegex("password", UserLocale.DEFAULT_LOCALE);
    }

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private LocalisationService localisationService;

    @RequestMapping
    public String registerRequestMapping(Map<String, Object> model) {
        model.put("lang", "ENG");
        model.put("locales", UserLocale.values());
        return "appPages/register";
    }

    @PostMapping
    @Deprecated
    public String registerPost(
            Map<String, Object> model,
            @RequestParam String password,
            @RequestParam String login,
            @RequestParam String role,
            @RequestParam String locale
    ) throws IllegalCredentialsException {
        //  refactor - move into service
        if(!password.matches(passwordPattern)) {
            throw new IllegalCredentialsException();
        }

        if(!login.matches(usernamePattern)) {
            throw new IllegalCredentialsException();
        }

        if(userRepository.findByUsername(login).isPresent()) {
            model.put("error", true);
            model.put("error-msg", localisationService.getLocalisedMessage("sentence.already-present-username", UserLocale.DEFAULT_LOCALE));
            return "appPages/register";
        }

        User newUser = User.userFactory(login, passwordEncoder.encode(password), role);
        newUser.setLocale(UserLocale.valueOf(locale));
        userRepository.save(newUser);

        return "redirect:/";
    }

    @ExceptionHandler({IllegalCredentialsException.class})
    private String handleException(IllegalCredentialsException exception) {
        Map<String, Object> model = new HashMap<>();
        model.put("error", true);
        model.put("error", exception);

        return registerRequestMapping(model);
    }
}
