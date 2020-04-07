package ua.axiom.controller.appController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.axiom.model.objects.User;
import ua.axiom.repository.AdminRepository;
import ua.axiom.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/adminpage")
public class AdminPageController {
    private UserRepository userRepository;

    private AdminRepository adminRepository;

    @Autowired
    public AdminPageController(UserRepository userRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    @RequestMapping
    public String getAdminPageControllerMapping(Map<String, Object> model) {
        System.out.println("adminpagecotmroller");

        List<User> users = userRepository.findAll();

        model.put("user-list", users);
        model.put("admin-list", users
                .stream()
                .filter(user -> user
                        .getAuthorities()
                        .stream()
                        .anyMatch(a -> a.toString().equals("ADMIN")))
                .collect(Collectors.toList()));

        return "/appPages/adminpage";
    }

    /*
    @PostMapping
    public void postBlockUser(@RequestParam String username) {
        System.out.println("block user: " + username);
    }

     */
}
