package ua.axiom.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.axiom.repository.UserRepository;
import ua.axiom.service.misc.MiscNulls;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder encoder;

    @Autowired
    public CustomUserDetailsService(UserRepository repository, PasswordEncoder encoder) {
        this.userRepository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return MiscNulls.getOrThrow(userRepository.findByUsername(s), new UsernameNotFoundException(s));

    }
}
