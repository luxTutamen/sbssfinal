package ua.axiom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.axiom.model.objects.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long> {

}
