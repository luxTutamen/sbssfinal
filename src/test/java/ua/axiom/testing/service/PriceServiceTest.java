package ua.axiom.testing.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ua.axiom.service.apiservice.PriceService;

import java.math.BigDecimal;

import static ua.axiom.testing.TestModelEntitiesCreator.getOrder;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PriceService.class)
public class PriceServiceTest {

    @InjectMocks
    private PriceService priceService;

    @Test
    public void testPrice() {

        BigDecimal price = priceService.getPrice(getOrder());

        Assert.assertNotNull(price);
    }
}
