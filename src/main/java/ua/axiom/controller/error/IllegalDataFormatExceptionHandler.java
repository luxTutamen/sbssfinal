package ua.axiom.controller.error;


import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.axiom.service.error.exceptions.IllegalDataFormatException;

@Component
public class IllegalDataFormatExceptionHandler {

    @ExceptionHandler(IllegalDataFormatException.class)
    public String exceptionHandler(IllegalDataFormatException e, Model model) {
        model.addAttribute("exception_cause", e.getCause());

        return "redirect:/error";
    }
}
