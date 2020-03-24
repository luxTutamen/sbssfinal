package ua.axiom.controller.appController;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/userpage")
public class UserpageController {
    @RequestMapping
    public String getUserPageController(Map<String, Object> model) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();


        model.put("username", authentication.getPrincipal());
        return "appPages/userpage";
    }
}
