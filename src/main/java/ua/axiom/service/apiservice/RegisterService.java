package ua.axiom.service.apiservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axiom.service.error.exceptions.UserAlreadyPresentException;
import ua.axiom.model.User;
import ua.axiom.repository.UserRepository;

@Service
public class RegisterService {
    private UserRepository userRepository;

    @Autowired
    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerNewUser(User user) throws UserAlreadyPresentException {
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyPresentException();
        }

        userRepository.save(user);
    }
}
