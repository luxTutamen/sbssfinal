package ua.axiom.controller.apiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.model.Role;
import ua.axiom.model.User;
import ua.axiom.model.UserFactory;
import ua.axiom.model.UserLocale;
import ua.axiom.service.LocalisationService;
import ua.axiom.service.apiservice.RegisterService;
import ua.axiom.service.error.LightVerboseException;
import ua.axiom.service.error.exceptions.FormInputException;
import ua.axiom.service.error.exceptions.GeneralRegisterException;
import ua.axiom.service.error.exceptions.UserAlreadyPresentException;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final String USERNAME_REGEX_PATTERN;
    private final String PASSWORD_REGEX_PATTERN;

    private final Set<Role> registrableRoles;

    private RegisterService registerService;
    private PasswordEncoder passwordEncoder;
    private LocalisationService localisationService;

    @Autowired
    public RegisterController(
            PasswordEncoder passwordEncoder,
            RegisterService registerService,
            LocalisationService localisationService
    ) {
        this.passwordEncoder = passwordEncoder;
        this.registerService = registerService;
        this.localisationService = localisationService;

        registrableRoles = getRegisterAccessibleRoles();

        USERNAME_REGEX_PATTERN = localisationService.getRegex("username", UserLocale.DEFAULT_LOCALE);
        PASSWORD_REGEX_PATTERN = localisationService.getRegex("password", UserLocale.DEFAULT_LOCALE);
    }

    @GetMapping
    protected ModelAndView formResponse(Model model) {
        model.addAttribute("roles", getRegisterAccessibleRoles());
        return new ModelAndView("appPages/register", model.asMap());
    }

    @PostMapping
    @Deprecated
    public String registerPost(
            @RequestParam String password,
            @RequestParam String login,
            @RequestParam Role role,
            @RequestParam UserLocale locale
    ) throws LightVerboseException, UserAlreadyPresentException {

        if (!registrableRoles.contains(role)) {
            throw new GeneralRegisterException();
        }

        if (!password.matches(PASSWORD_REGEX_PATTERN) | !login.matches(USERNAME_REGEX_PATTERN)) {
            throw new FormInputException();
        }

        User newUser = UserFactory.userFactory(login, passwordEncoder.encode(password), role, locale);
        registerService.registerNewUser(newUser);

        return "redirect:/";
    }

    @ExceptionHandler(FormInputException.class)
    public ModelAndView exception(FormInputException exception, Model model) {
        model.addAttribute("error", exception.getShortMessage(localisationService));
        return formResponse(model);
    }

    @ExceptionHandler({UserAlreadyPresentException.class})
    private ModelAndView handleUserPresentException(UserAlreadyPresentException exception, Model model) {
        model.addAttribute("error", exception.getShortMessage(localisationService));
        return formResponse(model);
    }

    private static Set<Role> getRegisterAccessibleRoles() {
        return Arrays
                .stream(Role.values())
                .filter(r -> r != Role.ADMIN && r != Role.GUEST)
                .collect(Collectors.toSet());
    }
}
