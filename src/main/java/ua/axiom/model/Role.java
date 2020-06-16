package ua.axiom.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    GUEST,
    CLIENT,
    DRIVER,
    ADMIN;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
