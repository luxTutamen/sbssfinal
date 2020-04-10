package ua.axiom.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public abstract class MultiViewController {
    private final Map<Set<GrantedAuthority>, Supplier<ModelAndView>> requestMapping = new HashMap<>();

    protected void addController(Set<GrantedAuthority> authorities, Supplier<ModelAndView> controller) {
        requestMapping.put(authorities, controller);
    }

    protected ModelAndView getRequestMapping() {
        GrantedAuthority authority = (GrantedAuthority) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .toArray()[0];

        return requestMapping
                .entrySet()
                    .stream()
                    .filter(e -> e.getKey().contains(authority))
                    .findFirst()
                .get()
                    .getValue()
                .get();
    }
}
