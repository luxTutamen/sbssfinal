package ua.axiom.service.userpersistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import ua.axiom.model.objects.User;

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
