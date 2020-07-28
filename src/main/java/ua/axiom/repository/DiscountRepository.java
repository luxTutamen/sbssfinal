package ua.axiom.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.axiom.model.Client;
import ua.axiom.model.Discount;

import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    List<Discount> getByClient(Client client, Pageable pageRequest);



    long countByClient(Client client);
}
