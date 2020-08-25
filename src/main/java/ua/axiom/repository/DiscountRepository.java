package ua.axiom.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.axiom.model.Client;
import ua.axiom.model.Discount;

import java.util.List;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    List<Discount> getByClient(Client client, Pageable pageRequest);

    Optional<Discount> findById(long id);

    long countByClient(Client client);
}
