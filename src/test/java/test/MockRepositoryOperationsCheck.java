package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import ua.axiom.model.User;
import ua.axiom.repository.AdminRepository;
import ua.axiom.repository.ClientRepository;
import ua.axiom.repository.DriverRepository;
import ua.axiom.repository.UserRepository;
import ua.axiom.testing.RepositoryMocksProvider;

import static ua.axiom.testing.TestModelEntitiesCreator.getClient;

@RunWith(MockitoJUnitRunner.class)
public class MockRepositoryOperationsCheck {

    //  todo autowire or something
    private static UserRepository userRepository = RepositoryMocksProvider.getUserRepositoryMock();
    private static ClientRepository clientRepository = RepositoryMocksProvider.getClientRepositoryMock();
    private static DriverRepository driverRepository = RepositoryMocksProvider.getDriverRepositoryMock();
    private static AdminRepository adminRepository = RepositoryMocksProvider.getAdminRepositoryMock();


    @Test
    public void testUserRepositoryMock() {
        User userA = getClient();

        userRepository.save(userA);
        userRepository.findByUsername(userA.getUsername());
        userRepository.getOne(userA.getId());
        userRepository.count();

    }
}
