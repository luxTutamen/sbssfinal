package service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import ua.axiom.model.Client;
import ua.axiom.repository.ClientRepository;
import ua.axiom.service.apiservice.ReplenishMoneyService;

import static org.mockito.ArgumentMatchers.longThat;
import static org.mockito.Mockito.when;
import static ua.axiom.testing.TestModelEntitiesCreator.getClient;

@RunWith(SpringRunner.class)
public class ReplenishMoneyServiceTest {

    @InjectMocks
    private ReplenishMoneyService replenishMoneyService;

    @Mock
    private ClientRepository clientRepository;


    @Test
    public void testReplenish() {
        Client client = getClient();

        when(clientRepository.getOne(longThat(id -> id.equals(client.getId())))).thenReturn(client);

        replenishMoneyService.replenish(client.getId());

        Assert.assertTrue(true);

    }
}
