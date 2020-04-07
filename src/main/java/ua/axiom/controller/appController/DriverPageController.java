package ua.axiom.controller.appController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/driverpage")
public class DriverPageController {
    @RequestMapping
    public String getDriverPage(Map<String, Object> model) {
        return "appPages/driverpage";
    }
}
