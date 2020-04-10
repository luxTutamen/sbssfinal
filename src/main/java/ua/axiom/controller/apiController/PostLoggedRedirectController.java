package ua.axiom.controller.apiController;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.axiom.model.objects.Role;

import java.util.Map;

@Controller
@RequestMapping("/api/plrdr")
public class PostLoggedRedirectController {

    @RequestMapping
    public String userDescRequestMapping(Map<String, Object> model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth.getAuthorities().contains(Role.ADMIN)) {
            return "redirect:/adminpage";
        } else if(auth.getAuthorities().contains(Role.DRIVER)) {
            return "redirect:/driverpage";
        } else if(auth.getAuthorities().contains(Role.CLIENT)) {
            return "redirect:/userpage";
        } else {
            return "redirect:/";
        }
    }
}
