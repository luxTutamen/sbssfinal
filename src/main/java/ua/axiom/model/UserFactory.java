package ua.axiom.model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class UserFactory {
    private static final Map<Role, Supplier<User>> constructors;

    static {
        constructors = new HashMap<>();

        constructors.put(Role.CLIENT, Client::new);
        constructors.put(Role.DRIVER, Driver::new);
        constructors.put(Role.ADMIN, Admin::new);
    }

    public static User userFactory(String login, String password, Role role, UserLocale locale, Object ... userSpecificData) throws RuntimeException {

        User user = constructors.get(role).get();

        user.setUsername(login);
        user.setPassword(password);
        user.setLocale(locale);

        user.setNotNullableFields(userSpecificData);

        return user;
    }
}
