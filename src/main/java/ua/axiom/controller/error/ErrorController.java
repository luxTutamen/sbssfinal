package ua.axiom.controller.error;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/error")
public class ErrorController {
    @GetMapping
    public String errorController(Model model) {
        return "error";
    }
}
