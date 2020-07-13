package ua.axiom.controller.apiController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//  @ControllerAdvice
@Controller
@RequestMapping("/error")
public class ErrorController {

    public String formResponse() {
        return "apiPages/error";
    }

    /*@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="IOException occured")
    @ExceptionHandler({Throwable.class})
    public void handleGeneralException(Exception e) {
    }*/

}
