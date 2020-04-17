package ua.axiom.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.axiom.model.objects.Client;
import ua.axiom.model.objects.Order;


import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, PagingAndSortingRepository<Order, Long> {
    //  List<Order> findWhereIdLimit(long id, long limit);
    List<Order> findByStatusAndUser(Order.Status status, Client user);

    List<Order> findByStatusAndUser(Pageable pageable, Order.Status status, Client user);

}
