package ua.axiom.service.apiservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axiom.model.objects.Client;
import ua.axiom.repository.ClientRepository;

import java.math.BigDecimal;

@Service
public class ReplenishMoneyService {
    private ClientRepository clientRepository;

    @Autowired
    public ReplenishMoneyService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void replenish(long id) {
        Client client = clientRepository.getOne(id);
        client.setMoney(client.getMoney().add(new BigDecimal("1000.00")));
        clientRepository.save(client);

    }
}
