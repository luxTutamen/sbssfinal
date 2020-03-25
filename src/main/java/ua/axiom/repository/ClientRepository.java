package ua.axiom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.axiom.model.objects.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
