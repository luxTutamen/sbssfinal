package ua.axiom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import ua.axiom.controller.exceptions.UserNotPresentException;
import ua.axiom.model.objects.User;
import ua.axiom.model.objects.UserLocale;

import java.util.Collection;
import java.util.Optional;

//  todo refactor optionals
public interface UserRepository extends JpaRepository<User, Long> {
    default UserLocale findLocaleById(Long id) throws UserNotPresentException {
        return this.findById(id).map(op -> op == null ? UserLocale.DEFAULT_LOCALE : op.getLocale()).get();
    }

    default GrantedAuthority findRoleById(long id) {
        return this.findById(id).get().getAuthorities().stream().findFirst().get();
    }

    default Collection<? extends GrantedAuthority> findRoleByUsername(String username) {
        return this.findByUsername(username).get().getAuthorities();
    }

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);


}
