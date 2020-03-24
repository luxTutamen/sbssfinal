package ua.axiom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class UsersListController {
//    @Autowired
//    UserRepository userRepository;

    @RequestMapping("/users")
    public String mainRequest() {
        return "appPages/userpage";
    }

    @GetMapping("/users")
    public String usersListGet(Map<String, Object> model) {
//        model.put("user-list", userRepository.findByRoleLike(Role.USER));
//        model.put("admin-list", userRepository.findByRoleLike(Role.ADMIN));

        return "appPages/users";
    }
}
