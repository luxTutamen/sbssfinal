package ua.axiom.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public abstract class MultiViewController {
    private final Map<Set<GrantedAuthority>, Function<ModelAndView, ModelAndView>> requestMapping = new HashMap<>();

    protected void addController(Set<GrantedAuthority> authorities, Function<ModelAndView, ModelAndView> controller) {
        requestMapping.put(authorities, controller);
    }

    protected ModelAndView getRequestMapping(ModelAndView previous) {
        GrantedAuthority authority = (GrantedAuthority) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .toArray()[0];

        return getViewController(authority)
                .apply(previous);
    }

    protected ModelAndView getRequestMapping(ModelAndView previous, GrantedAuthority authority) {
        return getViewController(authority)
                .apply(previous);
    }

    private Function<ModelAndView, ModelAndView> getViewController(GrantedAuthority authority) {
        return requestMapping
                .entrySet()
                .stream()
                .filter(e -> e.getKey().contains(authority))
                .findFirst()
                .get()
                .getValue();
    }
}
