package ua.axiom.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.axiom.model.objects.User;

import java.util.Optional;

public interface UserService extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

}
