package ua.axiom.service.appservice;

import org.springframework.stereotype.Service;
import ua.axiom.model.Client;
import ua.axiom.model.Discount;
import ua.axiom.repository.DiscountRepository;

import java.util.List;

@Service
public class DiscountService {
    private DiscountRepository discountRepository;

    public List<Discount> getAllDiscountsForClient(Client client) {
        return discountRepository.getByClient(client);
    }
}
