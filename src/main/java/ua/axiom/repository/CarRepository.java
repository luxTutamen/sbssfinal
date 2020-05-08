package ua.axiom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.axiom.model.objects.Car;

public interface CarRepository extends JpaRepository<Car, Long> {

}
