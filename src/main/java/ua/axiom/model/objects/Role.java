package ua.axiom.model.objects;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    GUEST,
    USER,
    DRIVER,
    ADMIN;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
