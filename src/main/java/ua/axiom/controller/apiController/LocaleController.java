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

import java.util.Arrays;

@Controller
@RequestMapping("/api/locale")
public class LocaleController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public void setLocale(@RequestParam String name) {
        long id =((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        User user = userRepository.findById(id).get();
        user.setLocale(UserLocale.valueOf(name));
        userRepository.save(user);

        ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).setLocale(UserLocale.valueOf(name));

    }


    private UserLocale strToLocale(String string) {
        return Arrays
                .stream(UserLocale.values())
                .filter(l -> l.toString().equals(string))
                .findAny()
                .get();
    }
}



