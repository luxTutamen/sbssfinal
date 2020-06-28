package ua.axiom.controller.apiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.axiom.model.User;
import ua.axiom.repository.UserRepository;

@Controller
@RequestMapping("/adminpage/ban")
public class BanController {
    private UserRepository userRepository;

    @Autowired
    public BanController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/ban")
    public String banMapping(@RequestParam long bannedId) {
        //  todo move into service
        User user = userRepository.getOne(bannedId);
        user.setBanned(true);
        userRepository.save(user);

        return "redirect:/adminpage";
    }
}
