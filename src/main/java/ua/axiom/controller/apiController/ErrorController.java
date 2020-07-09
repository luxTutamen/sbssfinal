package ua.axiom.controller.apiController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.ThymeleafController;
import ua.axiom.model.User;

//  todo
//  @Controller
@RequestMapping("/error")
public class ErrorController extends ThymeleafController<User> {
    @Override
    protected ModelAndView formResponse(Model model) {
        return new ModelAndView("apiPages/error", model.asMap());
    }

    @Override
    protected void fillUserSpecificData(Model model, User user) { }

    @ExceptionHandler({Throwable.class})
    public Model handleGeneralException(Exception e) {
        Model model = new ConcurrentModel();
        model.addAttribute("exceptionMsg", "Unknown exception");
        return model;
    }
}
