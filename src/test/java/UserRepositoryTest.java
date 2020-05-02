import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import ua.axiom.controller.exceptions.UserNotPresentException;
import ua.axiom.model.objects.Client;
import ua.axiom.model.objects.Discount;
import ua.axiom.model.objects.User;
import ua.axiom.model.objects.UserLocale;
import ua.axiom.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;

@RunWith(SpringRunner.class)
@DataJpaTest
@Component
public class UserRepositoryTest {
    @Autowired
    private static UserRepository userRepository;

    @BeforeClass
    public static void init() {
        User user = new Client(new Date(), new Date(), new BigDecimal("444.1"), new LinkedList<>(), false);
        user.setId(6666L);
        user.setUsername("6666EKR");
        user.setLocale(UserLocale.UKR);
        user.setPassword("a");
        userRepository.save(user);
    }

    @AfterClass
    public static void destruct() {
        userRepository.deleteById(6666L);
    }

    @Test
    public void getLocaleFromRepositoryTest() throws UserNotPresentException {
        UserLocale locale = userRepository.findLocaleById(6666L);
        Assert.assertEquals(locale, UserLocale.UKR);
    }
}
