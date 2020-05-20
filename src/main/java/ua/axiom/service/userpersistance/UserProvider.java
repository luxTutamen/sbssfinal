package ua.axiom.service.userpersistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import ua.axiom.model.objects.User;

/**
 * gets current logged user id from session context and returns this user from database
 * @param <T>
 */
public class UserProvider<T extends User> {
    private final JpaRepository<T, Long> repository;

    public UserProvider(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    public T getCurrentUserFromDB() {
        long id = ((T) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return repository.getOne(id);
    }
}
