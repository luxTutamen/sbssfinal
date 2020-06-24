package ua.axiom.controller.appController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.model.UserLocale;

@Controller("/test")
public class TestThymeleafController {
    @GetMapping
    public ModelAndView getTest() {
        ModelAndView modelAndView = new ModelAndView("appPages/clientpage");
        modelAndView.addObject("locales", UserLocale.values());
        modelAndView.addObject("username", "Dummy");
        modelAndView.addObject("current-locale_en", "EN");
        return modelAndView;
    }
}
