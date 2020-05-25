package ua.axiom.service.apiservice;

import org.springframework.stereotype.Service;
import ua.axiom.model.objects.Order;

import java.math.BigDecimal;
import java.util.Random;

@Service
//  todo discount
public class PriceService {
    private final Random random = new Random();

    public BigDecimal getPrice(Order order) {
        BigDecimal price = BigDecimal.valueOf(random.nextLong() % 500L + 500L);
        price = price.multiply(new BigDecimal(order.getCClass().multiplier));

        return price;
    }
}
