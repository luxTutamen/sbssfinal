package ua.axiom.service.appservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axiom.controller.exceptions.NotEnoughMoneyException;
import ua.axiom.model.objects.*;
import ua.axiom.repository.*;
import ua.axiom.service.apiservice.PriceService;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private ClientRepository clientRepository;
    private DriverRepository driverRepository;
    private PriceService priceService;

    private static final Random priceGenerator = new Random();

    @Autowired
    public OrderService(
            OrderRepository orderRepository,
            ClientRepository clientRepository,
            DriverRepository driverRepository,
            PriceService priceService
    ) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.driverRepository = driverRepository;
        this.priceService = priceService;
    }

    public void processNewOrder(Order order) throws NotEnoughMoneyException {

        BigDecimal price = priceService.getPrice(order);

        Client client = (Client) order.getClient();

        if (price.compareTo(client.getMoney()) == 1) {
            throw new NotEnoughMoneyException();
        }

        order.setPrice(price);
        client.setMoney(client.getMoney().subtract(price));

        orderRepository.save(order);
        clientRepository.save(client);
    }

    public void processFinishedOrder(long orderId) {
        Order order = orderRepository.getOne(orderId);
        Driver driver = (Driver) order.getDriver();

        if (order.isConfirmedByClient() && order.isConfirmedByDriver()) {
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
