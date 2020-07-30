package ua.axiom.testing.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.axiom.model.Client;
import ua.axiom.model.Driver;
import ua.axiom.model.User;
import ua.axiom.repository.ClientRepository;
import ua.axiom.service.appservice.ClientService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ua.axiom.testing.TestModelEntitiesCreator.*;

@RunWith(SpringRunner.class)
public class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Test
    public void findAllTest() {
        when(clientRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(getClientList()));

        List<Client> list = clientService.findAll(1, 1);

        verify(clientRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void findByIdTest() {
        Client user = getClient();

        when(clientRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User result = clientRepository.findById(user.getId()).get();

        Assert.assertEquals(result.getId(), user.getId());
        verify(clientRepository, times(1)).findById(anyLong());



    }
}
