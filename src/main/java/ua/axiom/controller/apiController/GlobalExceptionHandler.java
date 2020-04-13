package ua.axiom.controller.apiController;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.exceptions.UserNotPresentException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotPresentException.class)
    public String userNotPresentHandling() {
        Map<String, Object> model = new HashMap<>();
        model.put("exception-reason", "Wrong credentials");

        return "exceptionpage";

    }
}
