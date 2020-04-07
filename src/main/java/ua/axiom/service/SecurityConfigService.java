package ua.axiom.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.axiom.model.objects.Role;

import java.util.Collection;

@Service
public class SecurityConfigService {
    public static boolean isLoggedAs(Role role) {
        Collection authorities = getAuthorities();

        return authorities
                .stream()
                .anyMatch(a -> a.toString().equals(role.toString()));
    }

    public static boolean isLogged() {
        Collection authorities = getAuthorities();

        return authorities.stream().noneMatch(a -> a.toString().equals("ROLE_ANONYMOUS"));
    }

    private static Collection getAuthorities() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities();
    }
}
