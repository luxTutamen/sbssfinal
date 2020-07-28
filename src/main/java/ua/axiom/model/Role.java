package ua.axiom.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum Role implements GrantedAuthority {
    GUEST,
    CLIENT,
    DRIVER,
    ADMIN;

    private static final Set<Role> accessibleRoles = new HashSet<>(Arrays.asList(CLIENT, DRIVER));

    @Override
    public String getAuthority() {
        return this.toString();
    }

    public static Set<Role> getRegisterAccessibleRoles() {
        return accessibleRoles;
    }
}
