package ua.axiom.service.apiservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axiom.model.Client;
import ua.axiom.repository.ClientRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class ReplenishMoneyService {
    private ClientRepository clientRepository;

    @Autowired
    public ReplenishMoneyService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    public void replenish(long id) {
        Client client = clientRepository.getOne(id);
        client.setMoney(client.getMoney().add(new BigDecimal("1000.00")));
        clientRepository.save(client);

    }
}
