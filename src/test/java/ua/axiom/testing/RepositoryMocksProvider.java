package ua.axiom.testing;

import org.mockito.ArgumentMatcher;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.TestConfiguration;
import ua.axiom.model.*;
import ua.axiom.repository.*;
import ua.axiom.service.error.exceptions.UnsupportedOperationOnMockObjectAnswer;
import ua.axiom.testing.answers.DatabaseSaveAnswer;

import java.io.Serializable;
import java.util.HashSet;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.longThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.axiom.testing.TestModelEntitiesCreator.*;


@TestConfiguration
public class RepositoryMocksProvider {

    public static AdminRepository getAdminRepositoryMock() {
        AdminRepository repository = mock(AdminRepository.class);

        ArgumentMatcher<Long> adminExistsByIdMatcher = i -> i == ADMIN_ID;
        Answer<Admin> statefulRepositoryMockAnswer = new DatabaseSaveAnswer<>(new HashSet<>(getAdminList()), Admin::getUsername);

        when(repository.findAll()).thenReturn(getAdminList());
        when(repository.count()).thenReturn((long) getAdminList().size());
        when(repository.existsById(longThat(adminExistsByIdMatcher))).thenReturn(true);
        //  Mockito.when(repository.existsById(longThat(() ->existsByIdMatcher))).thenReturn(false);
        when(repository.getOne(longThat(adminExistsByIdMatcher))).thenReturn(getAdmin());
        when(repository.save(any())).then(statefulRepositoryMockAnswer);

        return repository;
    }

    public static CarRepository getCarRepositoryMock() {
        CarRepository repository = mock(CarRepository.class, new UnsupportedOperationOnMockObjectAnswer());

        ArgumentMatcher<Long> carExistsByIdMatcher = i -> i == CAR_ID;
        Answer<Car> statefulRepositoryMockAnswer = new DatabaseSaveAnswer<>(new HashSet<>(getCarList()), Car::getModelName);

        when(repository.findAll()).thenReturn(getCarList());
        when(repository.count()).thenReturn((long) getAdminList().size());
        when(repository.existsById(longThat(carExistsByIdMatcher))).thenReturn(true);
        //  Mockito.when(repository.existsById(longThat(() ->existsByIdMatcher))).thenReturn(false);
        when(repository.getOne(longThat(carExistsByIdMatcher))).thenReturn(getCar());
        when(repository.save(any())).then(statefulRepositoryMockAnswer);

        return repository;

    }

    public static ClientRepository getClientRepositoryMock() {
        ClientRepository repository = mock(ClientRepository.class);

        ArgumentMatcher<Long> clientExistsByIdMatcher = i -> i == CLIENT_ID;
        Answer<Client> statefulRepositoryMockAnswer = new DatabaseSaveAnswer<>(new HashSet<>(getClientList()), User::getUsername);

        when(repository.findAll()).thenReturn(getClientList());
        when(repository.count()).thenReturn((long) getAdminList().size());
        when(repository.existsById(longThat(clientExistsByIdMatcher))).thenReturn(true);
        //  Mockito.when(repository.existsById(longThat(() ->existsByIdMatcher))).thenReturn(false);
        when(repository.getOne(longThat(clientExistsByIdMatcher))).thenReturn(getClient());
        when(repository.save(any())).then(statefulRepositoryMockAnswer);

        return repository;
    }

    public static DiscountRepository getDiscountRepositoryMock() {
        //  DiscountRepository repository = mock(DiscountRepository.class, new UnsupportedOperationOnMockObjectAnswer());
        DiscountRepository repository = mock(DiscountRepository.class);

        ArgumentMatcher<Long> discountExistsByIdMatcher = i -> i == DISCOUNT_ID;
        Answer<Discount> statefulRepositoryMockAnswer = new DatabaseSaveAnswer<>(new HashSet<>(getDiscountList()), Discount::getId);

        when(repository.findAll()).thenReturn(getDiscountList());
        when(repository.count()).thenReturn((long) getAdminList().size());
        when(repository.existsById(longThat(discountExistsByIdMatcher))).thenReturn(true);
        //  Mockito.when(repository.existsById(longThat(() ->existsByIdMatcher))).thenReturn(false);
        when(repository.getOne(longThat(discountExistsByIdMatcher))).thenReturn(getDiscount());
        when(repository.save(any())).then(statefulRepositoryMockAnswer);

        return repository;

    }

    public static DriverRepository getDriverRepositoryMock() {
        DriverRepository repository = mock(DriverRepository.class);

        ArgumentMatcher<Long> discountExistsByIdMatcher = i -> i == DRIVER_ID;
        Answer<Driver> statefulRepositoryMockAnswer = new DatabaseSaveAnswer<>(User::getUsername);

        when(repository.findAll()).thenReturn(getDriverList());
        when(repository.count()).thenReturn((long) getAdminList().size());
        when(repository.existsById(longThat(discountExistsByIdMatcher))).thenReturn(true);
        //  Mockito.when(repository.existsById(longThat(() ->existsByIdMatcher))).thenReturn(false);
        when(repository.getOne(longThat(discountExistsByIdMatcher))).thenReturn(getDriver());
        when(repository.save(any())).then(statefulRepositoryMockAnswer);

        return repository;
    }

    public static OrderRepository getOrderRepositoryMock() {
        OrderRepository repository = mock(OrderRepository.class, new UnsupportedOperationOnMockObjectAnswer());

        ArgumentMatcher<Long> discountExistsByIdMatcher = i -> i == DRIVER_ID;
        Answer<Order> statefulRepositoryMockAnswer = new DatabaseSaveAnswer<>(new HashSet<>(getOrdersList()), Order::getId);

        when(repository.findAll()).thenReturn(getOrdersList());
        when(repository.count()).thenReturn((long) getAdminList().size());
        when(repository.existsById(longThat(discountExistsByIdMatcher))).thenReturn(true);
        //  Mockito.when(repository.existsById(longThat(() ->existsByIdMatcher))).thenReturn(false);
        when(repository.getOne(longThat(discountExistsByIdMatcher))).thenReturn(getOrder());
        when(repository.save(any())).then(statefulRepositoryMockAnswer);

        return repository;
    }

    public static UserRepository getUserRepositoryMock() {
        UserRepository repository = mock(UserRepository.class);

        Answer<User> savePersistingAnswer = new DatabaseSaveAnswer<>(new HashSet<>(getUserList()), User::getUsername);
        //Answer<User> findByUsernamePersistingAnswer = ;


        ArgumentMatcher<Long> userExistsByIdMatcher = i -> i > 0 && i <= getAdminList().size();

        when(repository.findAll()).thenReturn(getUserList());
        when(repository.count()).thenReturn((long) getAdminList().size());
        when(repository.existsById(longThat(userExistsByIdMatcher))).thenReturn(true);
        //  Mockito.when(repository.existsById(longThat(() ->existsByIdMatcher))).thenReturn(false);

        when(repository.save(any())).then(savePersistingAnswer);
        //  when(repository.findByUsername(any())).then(findByUsernamePersistingAnswer);

        return repository;
    }
}
