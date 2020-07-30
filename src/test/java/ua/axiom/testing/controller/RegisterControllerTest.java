package ua.axiom.testing.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.MapBindingResult;
import ua.axiom.controller.apiController.RegisterController;
import ua.axiom.model.Client;
import ua.axiom.model.Role;
import ua.axiom.model.UserLocale;
import ua.axiom.model.dto.RegisterFormDto;
import ua.axiom.security.PasswordEncoderProvider;
import ua.axiom.service.apiservice.RegisterService;
import ua.axiom.service.error.LightVerboseException;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static ua.axiom.testing.TestModelEntitiesCreator.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PasswordEncoderProvider.class} )
public class RegisterControllerTest {

    @Mock
    private RegisterService registerService;

    @InjectMocks
    private RegisterController registerController;


    @Test
    public void testRegister() throws LightVerboseException {
        RegisterFormDto formDto = RegisterFormDto.builder()
                .username("unique" + getUsername(Client.class))
                .locale(UserLocale.DEFAULT_LOCALE)
                .password(DEFAULT_PASSWORD)
                .role(Role.CLIENT)
                .build();

        registerController.registerPost(formDto, new MapBindingResult(Collections.EMPTY_MAP, ""));

        verify(registerService).registerNewUser(formDto);

    }

}
