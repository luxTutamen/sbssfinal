package ua.axiom.controller.apiController;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.axiom.PostLoggedRedirectConfiguration;

import java.util.Collection;
import java.util.Map;

/**
 * Redirects newly logged users according to their role
 */
@Controller
@RequestMapping("/api/plrdr")
public class PostLoggedRedirectController {

    @RequestMapping
    public String userDescRequestMapping(Map<String, Object> model) {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        return "redirect:" + PostLoggedRedirectConfiguration.configuration.get(authorities.iterator().next());
    }
}
