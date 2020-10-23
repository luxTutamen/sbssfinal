package ua.axiom.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.axiom.model.Car;
import ua.axiom.model.Client;
import ua.axiom.model.Driver;
import ua.axiom.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, PagingAndSortingRepository<Order, Long> {

    List<Order> findByStatusAndClient(Order.Status status, Client client);

    List<Order> findByStatusAndClient(Pageable pageable, Order.Status status, Client client);

    List<Order> findByStatusAndClientId(Pageable pageable, Order.Status status, long clientId);

    Order findByDriver(Driver driver);

    Order findByDriverAndStatus(Driver driver, Order.Status status);

    List<Order> findByCClass(Car.Class cClass);

    List<Order> findByCClassAndStatus(Car.Class cClass, Order.Status status);

    Optional<Order> findByIdAndStatus(Long id, Order.Status status);

    long countByClientAndStatus(Client client, Order.Status status);
}
