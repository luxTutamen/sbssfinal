package ua.axiom.controller.appController;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {
    @RequestMapping
    public String error() {
        return "/error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
