package ua.axiom.controller.appController.driverControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.MustacheController;
import ua.axiom.model.Driver;
import ua.axiom.model.Order;
import ua.axiom.model.UserLocale;
import ua.axiom.repository.DriverRepository;
import ua.axiom.repository.OrderRepository;
import ua.axiom.service.LocalisationService;

import java.util.Map;

@Component
public class WithOrderDriverController extends MustacheController<Driver> {
    private OrderRepository orderRepository;
    private LocalisationService localisationService;

    @Autowired
    public WithOrderDriverController(
            OrderRepository orderRepository,
            LocalisationService localisationService,
            DriverRepository driverRepository
    ) {
        this.orderRepository = orderRepository;
        this.localisationService = localisationService;
    }

    @Override
    protected ModelAndView formResponse(Map<String, Object> model) {
        return new ModelAndView("appPages/driverpages/withOrderDriverPage", model);
    }

    @Override
    protected void fillUserSpecificData(Map<String, Object> model, Driver user) {
        model.put("orders", orderRepository.findByCClassAndStatus(user.getCar().getAClass(), Order.Status.PENDING));
        model.put("balance", user.getBalance());
        model.put("car", user.getCar());

        Order order = orderRepository.getOne(user.getCurrentOrder().getId());

        model.put("departure", order.getDeparture());
        model.put("destination", order.getDestination());

        model.put("tax", order.getPrice());
    }

    @Override
    protected void fillLocalisedPageData(Map<String, Object> model, UserLocale userLocale) {
        localisationService.setLocalisedMessages(
                model,
                userLocale,
                "sentence.current-order-description-msg",
                "word.from",
                "word.to",
                "sentence.sentence-confirm-msg",
                "word.balance",
                "word.class",
                "word.car-model"
        );
    }

}