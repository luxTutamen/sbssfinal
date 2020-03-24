package ua.axiom.controller.appController;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Visible for drivers only
 */
@Controller
@RequestMapping("/cabinet")
public class CabinetController {
    @RequestMapping
    public String getCabinetControllerRequest() {
        return "appPages/cabinet";
    }


}
