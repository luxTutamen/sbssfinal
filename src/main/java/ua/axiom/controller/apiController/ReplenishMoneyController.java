package ua.axiom.controller.apiController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.axiom.model.objects.Client;
import ua.axiom.repository.ClientRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/replenish")
public class ReplenishMoneyController {

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping
    @Transactional
    public String getReplenishRequest() {
        Long id =  ((Client)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        Client client = clientRepository.findById(id).get();
        client.setMoney(client.getMoney().add(new BigDecimal("1000.00")));
        clientRepository.save(client);

        return "redirect:/";
    }
}
