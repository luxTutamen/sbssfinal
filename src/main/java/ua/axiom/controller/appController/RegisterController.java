package ua.axiom.controller.appController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/register")
public class RegisterController {
//    @Autowired
//    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping
    public String getRequestControllerMapping() {
        return "appPage/register";
    }

    @PostMapping
    public String registerControllerPost(
            @RequestParam String login,
            @RequestParam String password) {

        //  userRepository.save(RegisterController.userFactory(login, passwordEncoder.encode(password)));
        System.out.println("saved user " + login + " " + passwordEncoder.encode(password));

        return "/";
    }

//    private static User userFactory(String login, String encodedPSW) {
//        User user = new User();
//        user.setUsername(login);
//        user.setPassword(encodedPSW);
//        user.setRole(Role.USER);
//
//        return user;
//    }

}
