package ua.axiom.service.appservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axiom.model.Client;
import ua.axiom.model.Order;
import ua.axiom.repository.OrderRepository;

import java.util.List;

@Service
public class OrderHistoryService {
    private OrderRepository orderRepository;

    @Autowired
    public OrderHistoryService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getFinishedOrder(Client user) {
        return orderRepository.findByStatusAndClient(Order.Status.FINISHED, user);
    }
}
