package ua.axiom.controller.apiController;

import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;

@RestController("/error")
public class ErrorController {

    @ExceptionHandler({Throwable.class, TemplateInputException.class})
    public String formResponse(Throwable t) {
        //  todo log
        System.err.println(t);
        return "apiPages/:405";
    }

    @GetMapping
    public String errorController() {
        return "apiPages/error";
    }
}
