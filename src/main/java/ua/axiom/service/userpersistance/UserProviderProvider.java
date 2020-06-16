package ua.axiom.service.userpersistance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ua.axiom.model.Admin;
import ua.axiom.model.Client;
import ua.axiom.model.Driver;
import ua.axiom.model.User;
import ua.axiom.repository.AdminRepository;
import ua.axiom.repository.ClientRepository;
import ua.axiom.repository.DriverRepository;
import ua.axiom.repository.UserRepository;

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
