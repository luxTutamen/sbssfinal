package ua.axiom.testing;

import org.springframework.boot.test.context.TestConfiguration;
import ua.axiom.model.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@TestConfiguration
public class TestModelEntitiesCreator {
    public final static long CLIENT_ID = 1L;
    public final static long DRIVER_ID = 2L;
    public final static long ADMIN_ID = 3L;
    public final static long CAR_ID = 4L;
    public final static long ORDER_ID = 5L;
    public final static long DISCOUNT_ID = 6L;

    public final static String DEFAULT_PASSWORD = "1234567890as";
    public final static String DEFAULT_CAR_MODEL = "DefaultCarModel";
    public final static BigDecimal DEFAULT_MONEY = new BigDecimal("5000.00");
    public static final String DEFAULT_DEPARTURE = "Kyiv";
    public static final String DEFAULT_DESTINATION = "Mykolaiv";
    public static final String DEFAULT_ORDER_PRICE = "766.00";

    static Admin getAdmin() {
        return Admin.builder()
                .id(ADMIN_ID)
                .username(getUsername(Admin.class))
                .isBanned(false)
                .locale(UserLocale.DEFAULT_LOCALE)
                .password(DEFAULT_PASSWORD)
                .build();
    }

    static List<Admin> getAdminList() {
        return Collections.singletonList(getAdmin());
    }

    public static List<Client> getClientList() { return Collections.singletonList(getClient()); }

    public static Client getClient() {
        return Client.builder()
                .id(CLIENT_ID)
                .isBanned(false)
                .locale(UserLocale.DEFAULT_LOCALE)
                .username(getUsername(Client.class))
                .password(DEFAULT_PASSWORD)
                .birthDate(new Date())
                .money(DEFAULT_MONEY)
                .build();
    }

    public static List<Driver> getDriverList() {return Collections.singletonList(getDriver()); }

    public static Driver getDriver() {
        return Driver.builder()
                .id(DRIVER_ID)
                .username(getUsername(Driver.class))
                .password(DEFAULT_PASSWORD)
                .balance(DEFAULT_MONEY)
                .isBanned(false)
                .locale(UserLocale.DEFAULT_LOCALE)
                .car(getCar())
                .build();
    }

    public static List<User> getUserList() {
        return Arrays.asList(getAdmin(), getClient(), getDriver());
    }

    public static Car getCar() {
        return Car.builder()
                .id(CAR_ID)
                .modelName(DEFAULT_CAR_MODEL)
                .multiplier(0.75F)
                .aClass(Car.Class.BUSINESS)
                .build();
    }

    public static List<Car> getCarList() {
        return Collections.singletonList(getCar());
    }

    public static Order getOrder() {
        return Order.builder()
                .cClass(Car.Class.BUSINESS)
                .confirmedByClient(false)
                .confirmedByDriver(false)
                .client(getClient())
                .driver(getDriver())
                .id(ORDER_ID)
                .discount(getDiscount())
                .departure(DEFAULT_DEPARTURE)
                .destination(DEFAULT_DESTINATION)
                .date(new Date())
                .price(new BigDecimal(DEFAULT_ORDER_PRICE))
                .status(Order.Status.TAKEN)
                .build();
    }

    public static List<Order> getOrdersList() {
        return Collections.singletonList(getOrder());
    }

    public static Discount getDiscount() {
        return new Discount(getClient(), 90, Discount.DiscountType.RANDOM);
    }

    public static List<Discount> getDiscountList() {
        return Collections.singletonList(getDiscount());
    }

    public static String getUsername(Class<? extends User> forClass) {
        return forClass.getSimpleName();
    }

}
