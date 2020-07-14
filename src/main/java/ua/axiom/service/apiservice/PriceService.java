package ua.axiom.service.apiservice;

import org.springframework.stereotype.Service;
import ua.axiom.model.Discount;
import ua.axiom.model.Order;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class PriceService {
    private final Random random = new Random();

    public BigDecimal getPrice(Order order) {
        BigDecimal price = BigDecimal.valueOf(random.nextLong() % 500L + 500L);
        price = price.multiply(new BigDecimal(order.getCClass().multiplier));

        Discount discount = order.getDiscount();
        if(discount == null) {
            return price;
        }

        price = price.multiply(discount.getMultiplier());

        return price;
    }
}
