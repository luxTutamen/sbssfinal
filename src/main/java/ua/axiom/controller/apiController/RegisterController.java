package ua.axiom.controller.apiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.error.LightVerboseExceptionFactory;
import ua.axiom.controller.error.exceptions.IllegalCredentialsException;
import ua.axiom.controller.error.exceptions.UserAlreadyPresentException;
import ua.axiom.model.Role;
import ua.axiom.model.User;
import ua.axiom.model.UserFactory;
import ua.axiom.model.UserLocale;
import ua.axiom.service.LocalisationService;
import ua.axiom.service.apiservice.RegisterService;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/register")
public class RegisterController {
    //  todo move out
    private final String usernamePattern;
    private final String passwordPattern;

    private final Set<Role> registrableRoles;

    private RegisterService registerService;
    private PasswordEncoder passwordEncoder;
    private LightVerboseExceptionFactory exceptionFactory;

    @Autowired
    public RegisterController(
            PasswordEncoder passwordEncoder,
            RegisterService registerService,
            LightVerboseExceptionFactory exceptionFactory,
            LocalisationService localisationService
            ) {
        this.passwordEncoder = passwordEncoder;
        this.registerService = registerService;
        this.exceptionFactory = exceptionFactory;

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

        if(!registrableRoles.contains(role)) {
            throw exceptionFactory.createLocalisedException(
                    IllegalCredentialsException::new,
                    "errormsg.cannot-create-admin-account",
                    UserLocale.toLocaleFromSessionLocale(LocaleContextHolder.getLocale()));
        }

        if(!password.matches(passwordPattern)) {
            throw exceptionFactory.createLocalisedException(
                    IllegalCredentialsException::new,
                    "errormsg.password-not-matches-regex",
                    UserLocale.toLocaleFromSessionLocale(LocaleContextHolder.getLocale()));
        }

        if(!login.matches(usernamePattern)) {
            throw exceptionFactory.createLocalisedException(
                    IllegalCredentialsException::new,
                    "errormsg.username-not-matches-regex",
                    UserLocale.toLocaleFromSessionLocale(LocaleContextHolder.getLocale()));
        }

        User newUser = UserFactory.userFactory(login, passwordEncoder.encode(password), role, locale);
        registerService.registerNewUser(newUser);

        return "redirect:/";
    }

    @ExceptionHandler({IllegalCredentialsException.class})
    private ModelAndView handleException(IllegalCredentialsException exception, Model model) {

        model.addAttribute("error", true);
        model.addAttribute("error_msg", exception.getSimpleMessage());

        return formResponse(model.asMap());
    }

    @ExceptionHandler({UserAlreadyPresentException.class})
    private ModelAndView handleUserPresentException(UserAlreadyPresentException exception, Model model) {

        model.addAttribute("error", true);
        model.addAttribute("error_msg", exception.getSimpleMessage());

        return formResponse(model.asMap());
    }

    private static Set<Role> getRegisterAccessibleRoles() {
        return Arrays
                .stream(Role.values())
                .filter(r -> r != Role.ADMIN && r != Role.GUEST)
                .collect(Collectors.toSet());
    }
}
