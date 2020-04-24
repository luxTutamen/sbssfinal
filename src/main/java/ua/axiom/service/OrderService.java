package ua.axiom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axiom.model.objects.Client;
import ua.axiom.model.objects.Driver;
import ua.axiom.model.objects.Order;
import ua.axiom.repository.ClientRepository;
import ua.axiom.repository.DriverRepository;
import ua.axiom.repository.OrderRepository;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private ClientRepository clientRepository;
    private DriverRepository driverRepository;

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
