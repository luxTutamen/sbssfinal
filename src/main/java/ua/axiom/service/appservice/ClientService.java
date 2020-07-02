package ua.axiom.service.appservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.axiom.model.Client;
import ua.axiom.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll(int page, int size) {
        return clientRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public Optional<Client> findById(long id) {
        return clientRepository.findById(id);
    }

}
