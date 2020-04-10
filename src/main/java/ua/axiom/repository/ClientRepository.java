package ua.axiom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.axiom.model.objects.Client;
import ua.axiom.model.objects.Order;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {


}
