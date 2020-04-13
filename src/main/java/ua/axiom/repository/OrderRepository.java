package ua.axiom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.axiom.model.objects.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    //  List<Order> findWhereIdLimit(long id, long limit);
    List<Order> findByStatus(Order.Status status);
}
