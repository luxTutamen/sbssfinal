package ua.axiom.service.appservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axiom.controller.exceptions.NotEnoughMoneyException;
import ua.axiom.model.objects.*;
import ua.axiom.repository.*;

import java.math.BigDecimal;
import java.util.Random;

//  todo refactor
@Service
public class OrderService {
    private OrderRepository orderRepository;
    private ClientRepository clientRepository;
    private DriverRepository driverRepository;

    private static final Random priceGenerator = new Random();

    @Autowired
    public OrderService(
            OrderRepository orderRepository,
            ClientRepository clientRepository,
            DriverRepository driverRepository
    ) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.driverRepository = driverRepository;
    }

    public void processNewOrder(Order order) throws NotEnoughMoneyException {

        BigDecimal price = new BigDecimal(priceGenerator.nextInt() % 500 + 500 + ".0");
        price = price.multiply(new BigDecimal(order.getCClass().multiplier));

        Client client = (Client)order.getClient();

        if(price.compareTo(client.getMoney()) == 1) {
            throw new NotEnoughMoneyException();
        }

        order.setPrice(price);

        client.setMoney(client.getMoney().subtract(price));

        orderRepository.save(order);
    }

    public void processFinishedOrder(long orderId) {
        Order order = orderRepository.getOne(orderId);
        Driver driver = (Driver)order.getDriver();

        if(order.isConfirmedByClient() && order.isConfirmedByDriver()) {
            order.setStatus(Order.Status.FINISHED);
        } else {
            return;
        }

        driver.setBalance(driver.getBalance().add(order.getPrice()));
        driver.setCurrentOrder(null);

        driverRepository.save(driver);
        orderRepository.save(order);

    }
}
