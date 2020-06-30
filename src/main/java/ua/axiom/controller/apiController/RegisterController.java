package ua.axiom.controller.apiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.ArrayUtils;
import sun.security.util.ArrayUtil;
import ua.axiom.controller.ThymeleafController;
import ua.axiom.controller.error.exceptions.IllegalCredentialsException;
import ua.axiom.controller.error.exceptions.UserAlreadyPresentException;
import ua.axiom.model.Role;
import ua.axiom.model.User;
import ua.axiom.model.UserLocale;
import ua.axiom.repository.UserRepository;
import ua.axiom.service.LocalisationService;
import ua.axiom.service.apiservice.RegisterService;

import java.util.*;

@Controller
@RequestMapping("/register")
public class RegisterController {
    //  todo move out
    private final String usernamePattern;
    private final String passwordPattern;

    private final List<Role> registrableRoles;

    private RegisterService registerService;
    private PasswordEncoder passwordEncoder;
    private LocalisationService localisationService;


    @Autowired
    public RegisterController(
            PasswordEncoder passwordEncoder,
            LocalisationService localisationService,
            RegisterService registerService
    ) {
        this.passwordEncoder = passwordEncoder;
        this.localisationService = localisationService;
        this.registerService = registerService;

        registrableRoles = getRegisterAccessibleRoles();

        usernamePattern = localisationService.getRegex("username", UserLocale.DEFAULT_LOCALE);
        passwordPattern = localisationService.getRegex("password", UserLocale.DEFAULT_LOCALE);
    }

    @GetMapping
    protected ModelAndView formResponse(Map<String, Object> model) {
        model.put("roles", getRegisterAccessibleRoles());
        return new ModelAndView("appPages/register", model);
    }

    @PostMapping
    @Deprecated
    public String registerPost(
            @RequestParam String password,
            @RequestParam String login,
            @RequestParam Role role,
            @RequestParam UserLocale locale
    ) throws IllegalCredentialsException, UserAlreadyPresentException {

        if(!password.matches(passwordPattern)) {
            throw new IllegalCredentialsException();
        }

        if(!login.matches(usernamePattern)) {
            throw new IllegalCredentialsException();
        }

        User newUser = User.userFactory(login, passwordEncoder.encode(password), role, locale);

        registerService.registerNewUser(newUser);

        return "redirect:/";
    }

    @ExceptionHandler({IllegalCredentialsException.class})
    private ModelAndView handleException(IllegalCredentialsException exception, Model model) {

        model.addAttribute("error", exception.getSimpleMessage());
        model.addAttribute("error_msg", exception.getSimpleMessage());

        return formResponse(model.asMap());
    }

    @ExceptionHandler({UserAlreadyPresentException.class})
    private ModelAndView handleUserPresentException(UserAlreadyPresentException exception, Model model) {

        model.addAttribute("error", true);
        model.addAttribute("error_msg", localisationService.getLocalisedMessage("sentence.already-present-username", UserLocale.DEFAULT_LOCALE));

        return formResponse(model.asMap());
    }

    private static List<Role> getRegisterAccessibleRoles() {
        List<Role> result = Arrays.asList(Role.values());
        result = new ArrayList<>(result);

        result.remove(Role.ADMIN);

        return result;
    }
}
