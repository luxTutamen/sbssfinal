package ua.axiom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axiom.model.objects.Admin;
import ua.axiom.model.objects.Client;
import ua.axiom.model.objects.Driver;
import ua.axiom.model.objects.User;
import ua.axiom.service.userpersistance.UserProvider;

@Service
public class TEstService {
    @Autowired
    private UserProvider<Client> clientProvider;
    @Autowired
    private UserProvider<Driver> driverProvider;
    @Autowired
    private UserProvider<Admin> adminProvider;
    @Autowired
    private UserProvider<User> userProvider;

    public void invoke() {
        System.out.println(clientProvider);
        System.out.println(driverProvider);
        System.out.println(adminProvider);
        System.out.println(userProvider);
    }
}
