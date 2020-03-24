package ua.axiom.controller.appController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adminpage")
public class AdminpageController {
    @RequestMapping
    public String getAdminpageControllerMapping() {
        return "appPages/adminpage";
    }
}
