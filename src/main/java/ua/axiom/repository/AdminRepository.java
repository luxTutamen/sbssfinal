package ua.axiom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.axiom.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

}
