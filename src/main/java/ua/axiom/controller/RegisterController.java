package ua.axiom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

import ua.axiom.model.objects.*;
import ua.axiom.repository.AdminRepository;
import ua.axiom.repository.ClientRepository;
import ua.axiom.repository.DriverRepository;
import ua.axiom.repository.UserRepository;

@Controller
@RequestMapping("/register")
public class RegisterController {
//    @Autowired
//    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping
    public String registerRequestMapping(Map<String, Object> model) {
        model.put("lang", "ENG");
        System.out.println("register request");
        return "appPages/register";
    }

    @PostMapping
    @Deprecated
    public String registerPost(
            Map<String, Object> model,
            @RequestParam String password,
            @RequestParam String login,
            @RequestParam String role
    ) {
        if(userRepository.findByUsername(login).isPresent()) {
            model.put("error", "Username is already present");
            return "appPages/register";
        };

        User newUser = User.userFactory(login, passwordEncoder.encode(password), role);
        newUser.setLocale(UserLocale.ENG);

        System.out.println("registering " + login + " as " + role);

        userRepository.save(newUser);

        return "redirect:/";

    }


    /*
    @PostMapping
    public String processRegistration(RegistrationForm form) {
        userRepository.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }

     */
}
