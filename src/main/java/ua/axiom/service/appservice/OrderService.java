package ua.axiom.service.appservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axiom.controller.error.exceptions.NotEnoughMoneyException;
import ua.axiom.model.Client;
import ua.axiom.model.Driver;
import ua.axiom.model.Order;
import ua.axiom.model.User;
import ua.axiom.repository.ClientRepository;
import ua.axiom.repository.DriverRepository;
import ua.axiom.repository.OrderRepository;
import ua.axiom.service.apiservice.PriceService;

import javax.transaction.Transactional;
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

    @Transactional
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

    @Transactional
    public void confirmByDriver(long orderId) {
        Order order = orderRepository.getOne(orderId);
        order.setConfirmedByDriver(true);
        orderRepository.save(order);

        if(order.isConfirmedByClient()) {
            finishOrder(order);
        }
    }

    @Transactional
    public void confirmByClient(long orderId) {
        Order order = orderRepository.getOne(orderId);
        order.setConfirmedByClient(true);
        orderRepository.save(order);

        if(order.isConfirmedByDriver()) {
            finishOrder(order);
        }
    }

    @Transactional
    public void cancelOrder(long orderId) {

        Order order = orderRepository.findById(orderId).get();
        order.setStatus(Order.Status.FINISHED);
        orderRepository.save(order);
    }

    private void finishOrder(Order order) {
        Driver driver = (Driver) order.getDriver();

        driver.setBalance(driver.getBalance().add(order.getPrice()));
        driver.setCurrentOrder(null);

        order.setStatus(Order.Status.FINISHED);

        driverRepository.save(driver);
        orderRepository.save(order);
    }
}
