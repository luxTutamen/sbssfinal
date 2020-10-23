package ua.axiom.controller.apiController;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thymeleaf.exceptions.TemplateInputException;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler({Throwable.class, TemplateInputException.class})
    public String formResponse(Throwable t) {
        //  todo log
        System.err.println(t);
        return "redirect/:405";
    }

}
