package ua.axiom.service.apiservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axiom.model.objects.User;
import ua.axiom.model.objects.UserLocale;
import ua.axiom.repository.UserRepository;

@Service
public class LocaleService {

    public UserRepository userRepository;

    @Autowired
    public LocaleService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setLocale(String locale, long userID) {

        User user = userRepository.findById(userID).get();
        user.setLocale(UserLocale.valueOf(locale));
        userRepository.save(user);

    }
}
