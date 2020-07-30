package ua.axiom.service.appservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.axiom.model.Admin;
import ua.axiom.model.Client;
import ua.axiom.model.Driver;
import ua.axiom.model.User;
import ua.axiom.repository.AdminRepository;
import ua.axiom.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AdminService {

    private ClientService clientService;
    private DriverService driverService;
    private AdminRepository adminRepository;

    private UserRepository userRepository;

    @Autowired
    public AdminService(
            ClientService clientService,
            DriverService driverService,
            AdminRepository adminRepository,
            UserRepository userRepository
    ) {
        this.clientService = clientService;
        this.driverService = driverService;
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
    }

    public List<Client> getClients(int page, int size) {
        return clientService.findAll(page, size);
    }

    public List<Driver> getDrivers(int page, int size) {
        return driverService.findAll(page, size);
    }

    public List<Admin> getAdmins(int page, int size) {
        return adminRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Transactional
    public void banUser(long userId) {
        User user = userRepository.findById(userId).get();

        user.setBanned(true);

        userRepository.save(user);
    }

    @Transactional
    public void promoteUser(long userId) {
        User user = userRepository.findById(userId).get();

        Admin newAdmin = new Admin();
        newAdmin.setId(user.getId());
        newAdmin.setUsername(user.getUsername());
        newAdmin.setPassword(user.getPassword());

        userRepository.delete(user);

        adminRepository.save(newAdmin);
    }
}
