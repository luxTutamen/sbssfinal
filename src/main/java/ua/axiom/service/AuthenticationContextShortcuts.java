package ua.axiom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.axiom.model.objects.User;
import ua.axiom.repository.UserRepository;

@Service
public class AuthenticationContextShortcuts {

    @Autowired
    private static UserRepository userRepository;

    public static GrantedAuthority getRole() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .findFirst()
                .get();
    }

    public static long getUserId() {
        return ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    public static User getPersistedUser() {
        return userRepository.findById(getUserId()).get();
    }
}
