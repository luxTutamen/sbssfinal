package ua.axiom.service.appservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axiom.model.Client;
import ua.axiom.model.Discount;
import ua.axiom.repository.DiscountRepository;

import java.util.Calendar;
import java.util.Date;

@Service
public class PromoService {
    private static final long SECS_IN_WEEK = 1000 * 60 * 60 * 24 * 7;
    private final Calendar calendar1 = Calendar.getInstance();
    private final Calendar calendar2 = Calendar.getInstance();

    private DiscountRepository discountRepository;

    @Autowired
    public PromoService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public void onClientLoad(Client client) {
        Date currDate = new Date();

        if(discountRepository.countByClient(client) > 10) {
            return;
        }

        if(client.getLastDiscountGiven().getTime() < (currDate.getTime() - SECS_IN_WEEK)) {
            discountRepository.save(new Discount(
                    client,
                    75,
                    Discount.DiscountType.RANDOM
            ));
            client.setLastDiscountGiven(new Date());
        }

        calendar1.setTimeInMillis(currDate.getTime());
        calendar2.setTimeInMillis(client.getBirthDate().getTime());

        if(!client.isReceivedBDayPromoToday() && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)) {
            discountRepository.save(new Discount(client, 50, Discount.DiscountType.B_DAY));
            client.setReceivedBDayPromoToday(true);
        }

        if(client.isReceivedBDayPromoToday() && calendar1.get(Calendar.DAY_OF_YEAR) != calendar2.get(Calendar.DAY_OF_YEAR)) {
            client.setReceivedBDayPromoToday(false);
        }
    }
}
