package ua.axiom.testing;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import ua.axiom.model.*;
import ua.axiom.repository.DiscountRepository;
import ua.axiom.service.apiservice.ReplenishMoneyService;
import ua.axiom.service.appservice.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;

/*
@Import()
public class ServiceMocksConfiguration {
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ReplenishMoneyService getReplenishMoneyServiceMockBean() {
        ReplenishMoneyService service = mock(ReplenishMoneyService.class);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public AdminService getAdminServiceMockBean() {
        AdminService service = mock(AdminService.class);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ClientService getClientServiceMockBean() {
        ClientService service = mock(ClientService.class);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public DiscountService getDiscountServiceMockBean() {
        DiscountService service = mock(DiscountService.class);
    }

    @Bean
    public DriverService getDriverServiceMockBean() {

    }

    @Bean
    public OrderHistoryService getOrderHistoryServiceMockBean() {

    }

    @Bean
    public OrderService getOrderServiceMockBean() {

    }

    @Bean
    public PromoService getPromoServiceMockBean() {

    }



}
*/
