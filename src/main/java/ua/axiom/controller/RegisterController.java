package ua.axiom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//  import ua.sbtaskf.repository.UserRepository;

import java.util.Map;


@Controller
@RequestMapping("/register")
public class RegisterController {
//    @Autowired
//    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    private PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @GetMapping
    public String registerRequestMapping(Map<String, Object> model) {
        model.put("lang", "ENG");
        return "register";
    }


    /*
    @PostMapping
    public String processRegistration(RegistrationForm form) {
        userRepository.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }

     */
}
