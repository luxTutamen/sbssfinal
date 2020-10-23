package ua.axiom.service.appservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.axiom.model.Car;
import ua.axiom.service.error.exceptions.JustTakenException;
import ua.axiom.service.error.exceptions.NotEnoughMoneyException;
import ua.axiom.model.Client;
import ua.axiom.model.Driver;
import ua.axiom.model.Order;
import ua.axiom.repository.ClientRepository;
import ua.axiom.repository.DriverRepository;
import ua.axiom.repository.OrderRepository;
import ua.axiom.service.apiservice.PriceService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
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

    public List<Order> findByStatusAndClient(Pageable pageable, Order.Status status, Client client) {
        return orderRepository.findByStatusAndClient(pageable, status, client);
    }

    public long countByClientAndStatus(Client client, Order.Status status) {
        return orderRepository.countByClientAndStatus(client, status);
    }

    public List<Order> findByCClassAndStatus(Pageable pageable, Car.Class cclass, Order.Status status) {
        return orderRepository.findByCClassAndStatus(cclass, status);
    }

    @Transactional(rollbackOn = {NotEnoughMoneyException.class})
    public void processNewOrder(Order order) throws NotEnoughMoneyException {

        BigDecimal price = priceService.getPrice(order);

        Client client = (Client) order.getClient();

        if (price.compareTo(client.getMoney()) > 0) {
            throw new NotEnoughMoneyException();
        }

        order.setPrice(price);
        client.setMoney(client.getMoney().subtract(price));

        orderRepository.save(order);
        clientRepository.save(client);
    }

    @Transactional
    public void confirmByDriver(long driverID) {
        Driver driver = driverRepository.getOne(driverID);
        Order order = driver.getCurrentOrder();

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

    @Transactional(rollbackOn = {JustTakenException.class})
    public void cancelOrder(long orderId) throws JustTakenException {

        Order order = orderRepository.findById(orderId).get();

        if(order.getStatus() == Order.Status.TAKEN) {
            throw new JustTakenException();
        }
        Client client = (Client)order.getClient();

        order.setStatus(Order.Status.FINISHED);
        client.setMoney(client.getMoney().add(order.getPrice()));

        clientRepository.save(client);
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
