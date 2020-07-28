package service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ua.axiom.model.Client;
import ua.axiom.model.Role;
import ua.axiom.model.User;
import ua.axiom.model.UserLocale;
import ua.axiom.model.dto.RegisterFormDto;
import ua.axiom.repository.UserRepository;
import ua.axiom.security.PasswordEncoderProvider;
import ua.axiom.service.apiservice.RegisterService;
import ua.axiom.service.error.exceptions.UserAlreadyPresentException;
import ua.axiom.testing.answers.DatabaseExistsAnswer;
import ua.axiom.testing.answers.DatabaseSaveAnswer;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static ua.axiom.testing.TestModelEntitiesCreator.DEFAULT_PASSWORD;
import static ua.axiom.testing.TestModelEntitiesCreator.getUsername;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PasswordEncoderProvider.class)
public class RegisterServiceTest {

    @Autowired
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RegisterService registerServices;

    private static RegisterFormDto formDto = RegisterFormDto.builder()
            .username("unique" + getUsername(Client.class))
            .locale(UserLocale.DEFAULT_LOCALE)
            .password(DEFAULT_PASSWORD)
            .role(Role.CLIENT)
            .build();


    @Test
    public void testNewUserRegister() {
        try {
            registerServices.registerNewUser(formDto);
        } catch (UserAlreadyPresentException e) {
            Assert.fail();
        }

        verify(userRepository, times(1));

    }

    @Test(expected = UserAlreadyPresentException.class)
    public void testExistedUserRegister() throws UserAlreadyPresentException {
        Set<String> keyRepository = new HashSet<>();
        DatabaseExistsAnswer<User, String> repositoryExistsAnswer = new DatabaseExistsAnswer<>(keyRepository, User::getUsername);
        DatabaseSaveAnswer<User, String> repositorySaveAnswer = new DatabaseSaveAnswer<>(keyRepository, User::getUsername);

        when(userRepository.exists(any())).then(repositoryExistsAnswer);
        when(userRepository.save(any(User.class))).then(repositorySaveAnswer);

        registerServices.registerNewUser(formDto);
        registerServices.registerNewUser(formDto);
    }

}
