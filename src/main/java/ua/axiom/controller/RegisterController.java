package ua.axiom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.axiom.controller.exceptions.IllegalCredentialsException;
import ua.axiom.model.objects.Role;
import ua.axiom.model.objects.User;
import ua.axiom.model.objects.UserLocale;
import ua.axiom.repository.UserRepository;
import ua.axiom.service.GuiService;
import ua.axiom.service.LocalisationService;

import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private static final String usernamePattern = "([\\w\\d]){5,40}";
    private static final String passwordPattern = "([\\w\\d]){8,40}";

    @Autowired
    public RegisterController(
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            LocalisationService localisationService
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.localisationService = localisationService;
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
            @RequestParam String role
    ) throws IllegalCredentialsException {
        if(userRepository.findByUsername(login).isPresent()) {
            model.put("error", true);
            model.put("error-msg", localisationService.getLocalisedMessage(UserLocale.DEFAULT_LOCALE.toJavaLocale(), "sentence.already-present-username"));
            return "appPages/register";
        }

        if(!password.matches(passwordPattern)) {
            model.put("error", true);
            model.put("error-msg", "password doesn't match the requirements!");
            return registerRequestMapping(model);
        }

        if(!login.matches(usernamePattern)) {
            model.put("error", true);
            model.put("error-msg", "username doesn't match the requirements!");
            return registerRequestMapping(model);
        }

        if(Role.valueOf(role) == Role.DRIVER) {
            return "appPages/driverpages/driverRegisterDetails";
        }

        User newUser = User.userFactory(login, passwordEncoder.encode(password), role);
        newUser.setLocale(UserLocale.ENG);

        userRepository.save(newUser);

        return "redirect:/";

    }
}
