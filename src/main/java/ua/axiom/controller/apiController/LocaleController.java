package ua.axiom.controller.apiController;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.axiom.model.objects.User;
import ua.axiom.model.objects.UserLocale;

import java.util.Arrays;

@Controller
@RequestMapping("/api/locale")
public class LocaleController {
    @PostMapping
    public void setLocale(@RequestParam String name) {
        System.out.println(name);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        user.setLocale(strToLocale(name));
    }

    private UserLocale strToLocale(String string) {
        return Arrays
                .stream(UserLocale.values())
                .filter(l -> l.toString().equals(string))
                .findAny()
                .get();
    }
}



