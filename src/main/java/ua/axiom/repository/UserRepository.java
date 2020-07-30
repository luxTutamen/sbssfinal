package ua.axiom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import ua.axiom.model.Role;
import ua.axiom.service.error.exceptions.UserNotPresentException;
import ua.axiom.model.User;
import ua.axiom.model.UserLocale;

import java.util.*;


public interface UserRepository extends JpaRepository<User, Long> {
    //  todo remove default methods
    default UserLocale findLocaleById(Long id) throws UserNotPresentException {
        return this.findById(id).map(User::getLocale).orElseGet(() -> UserLocale.DEFAULT_LOCALE);
    }


    default GrantedAuthority findRoleById(long id) {
        return this.findById(id)
                .map(User::getAuthorities)
                .map(Collection::iterator)
                .map(Iterator::next)
                .orElse(Role.GUEST);
    }

    default Role findRoleByUsername(String username) {
        return this.findByUsername(username)
                .map(User::getAuthorities)
                .map(Collection::iterator)
                .map(Iterator::next)
                .orElse(Role.GUEST);
    }

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);
}
