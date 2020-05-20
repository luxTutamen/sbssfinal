package ua.axiom.service.userpersistance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ua.axiom.model.objects.*;
import ua.axiom.repository.*;

@Component
public class UserProviderProvider {
    @Bean
    @Autowired
    public UserProvider<User> getUserProvider(UserRepository repository) {
        return new UserProvider<>(repository);
    }

    @Bean
    @Autowired
    public UserProvider<Client> getClientProvider(ClientRepository repository) {
        return new UserProvider<>(repository);
    }

    @Bean
    @Autowired
    public UserProvider<Driver> getDriverProvider(DriverRepository repository) {
        return new UserProvider<>(repository);
    }

    @Bean
    @Autowired
    public UserProvider<Admin> getAdminProvider(AdminRepository repository) {
        return new UserProvider<>(repository);
    }

}
