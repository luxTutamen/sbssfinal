package ua.axiom.service.apiservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.axiom.model.User;
import ua.axiom.model.UserFactory;
import ua.axiom.model.dto.RegisterFormDto;
import ua.axiom.repository.UserRepository;
import ua.axiom.service.error.exceptions.UserAlreadyPresentException;

import javax.transaction.Transactional;

@Service
public class RegisterService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(rollbackOn = UserAlreadyPresentException.class)
    public void registerNewUser(RegisterFormDto registerFormDto) throws UserAlreadyPresentException {

        User newUser = UserFactory
                .userFactory(
                        registerFormDto.getUsername(),
                        passwordEncoder.encode(registerFormDto.getPassword()),
                        registerFormDto.getRole(),
                        registerFormDto.getLocale());

        if(userRepository.exists(Example.of(newUser, ExampleMatcher.matching().withIgnorePaths("password", "role", "id", "birthDate", "lastDiscountGiven", "money", "locale")))) {
            throw new UserAlreadyPresentException();
        }

        userRepository.save(newUser);
    }
}
