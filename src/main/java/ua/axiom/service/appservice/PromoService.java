package ua.axiom.service.appservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axiom.model.objects.Client;
import ua.axiom.model.objects.Discount;
import ua.axiom.repository.DiscountRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class PromoService {
    private static final long week = 1000 * 60 * 60 * 24 * 7;
    private final Calendar calendar1 = Calendar.getInstance();
    private final Calendar calendar2 = Calendar.getInstance();

    @Autowired
    private DiscountRepository discountRepository;

    public void onClientLoad(Client client) {
        Date currDate = new Date();

        if(client.getLastDiscountGiven().getTime() < (currDate.getTime() - week)) {
            discountRepository.save(new Discount(
                    client,
                    0.75F,
                    Discount.DiscountType.RANDOM
            ));
            client.setLastDiscountGiven(new Date());
        }

        calendar1.setTimeInMillis(currDate.getTime());
        calendar2.setTimeInMillis(client.getBirthDate().getTime());

        if(!client.isReceivedBDayPromoToday() && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)) {
            discountRepository.save(new Discount(client, 0.5F, Discount.DiscountType.B_DAY));
            client.setReceivedBDayPromoToday(true);
        }

        if(client.isReceivedBDayPromoToday() && calendar1.get(Calendar.DAY_OF_YEAR) != calendar2.get(Calendar.DAY_OF_YEAR)) {
            client.setReceivedBDayPromoToday(false);
        }
    }


}
