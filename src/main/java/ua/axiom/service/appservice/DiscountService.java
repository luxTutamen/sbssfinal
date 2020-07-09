package ua.axiom.service.appservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axiom.model.Client;
import ua.axiom.model.Discount;
import ua.axiom.repository.DiscountRepository;

import java.util.List;

@Service
public class DiscountService {
    private DiscountRepository discountRepository;

    @Autowired
    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public Discount findOne(long id) {
        return discountRepository.getOne(id);
    }

    public List<Discount> getAllDiscountsForClient(Client client) {
        return discountRepository.getByClient(client);
    }
}
