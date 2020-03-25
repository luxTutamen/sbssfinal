package ua.axiom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

import ua.axiom.model.objects.User;
import ua.axiom.model.objects.Client;
import ua.axiom.repository.ClientRepository;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

//  import ua.sbtaskf.repository.UserRepository;


@Controller
@RequestMapping("/register")
public class RegisterController {
//    @Autowired
//    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientRepository clientRepository;

    @Bean
    private PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @RequestMapping
    public String registerRequestMapping(Map<String, Object> model) {
        model.put("lang", "ENG");
        return "appPages/register";
    }

    @PostMapping
    public String registerPost(
            Map<String, Object> model,
            @RequestParam String password,
            @RequestParam String login) {
        Client user = new Client();
        user.setUsername(login);
        user.setBirthDate(new Date(6234213L));
        user.setMoney(0.F);
        user.setPassword(passwordEncoder.encode(password));
        System.out.println(user.getPassword().length());

        clientRepository.save(user);

        return "/";

    }


    /*
    @PostMapping
    public String processRegistration(RegistrationForm form) {
        userRepository.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }

     */
}
