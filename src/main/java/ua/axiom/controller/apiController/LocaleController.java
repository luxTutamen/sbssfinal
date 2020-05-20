package ua.axiom.controller.apiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.axiom.model.objects.User;
import ua.axiom.model.objects.UserLocale;
import ua.axiom.repository.UserRepository;
import ua.axiom.service.apiservice.LocaleService;

@Controller
@RequestMapping("/api/locale")
public class LocaleController {

    @Autowired
    private LocaleService localeService;

    @PostMapping
    public void setLocale(@RequestParam String name) {
        User user =((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        //  todo asc pass whole user or just an ID?
        localeService.setLocale(name, user.getId());

        user.setLocale(UserLocale.valueOf(name));

    }

}



