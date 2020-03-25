package ua.axiom.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.axiom.model.objects.User;
import ua.axiom.service.UserService;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
        return user;
    }
}
