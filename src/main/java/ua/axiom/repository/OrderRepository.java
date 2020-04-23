package ua.axiom.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.axiom.model.objects.*;


import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, PagingAndSortingRepository<Order, Long> {
    //  List<Order> findWhereIdLimit(long id, long limit);
    List<Order> findByStatusAndClient(Order.Status status, Client client);

    List<Order> findByStatusAndClient(Pageable pageable, Order.Status status, Client client);

    Order findByDriver(Driver driver);

    Order findByDriverAndStatus(Driver driver, Order.Status status);

    List<Order> findByCClass(Car.Class cClass);
    
    List<Order> findByCClassAndStatus(Car.Class cClass, Order.Status status);

}
