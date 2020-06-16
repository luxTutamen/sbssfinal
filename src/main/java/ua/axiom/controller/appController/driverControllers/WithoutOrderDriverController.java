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
public class WithoutOrderDriverController extends MustacheController<Driver> {
    private LocalisationService localisationService;
    private OrderRepository orderRepository;

    @Autowired
    public WithoutOrderDriverController(
            DriverRepository driverRepository,
            LocalisationService localisationService,
            OrderRepository orderRepository
    ) {
        this.localisationService = localisationService;
        this.orderRepository = orderRepository;
    }

    @Override
    protected ModelAndView formResponse(Map<String, Object> model) {
        return new ModelAndView("appPages/driverpages/noOrderDriverpage", model);
    }

    @Override
    protected void fillUserSpecificData(Map<String, Object> model, Driver user) {
        model.put("orders", orderRepository.findByCClassAndStatus(user.getCar().getAClass(), Order.Status.PENDING));
        model.put("balance", user.getBalance());
        model.put("car", user.getCar());
    }

    @Override
    protected void fillLocalisedPageData(Map<String, Object> model, UserLocale userLocale) {
        localisationService.setLocalisedMessages(
                model,
                userLocale,
                "sentence.take-order",
                "sentence.accessible-orders",
                "word.withdraw",
                "sentence.your-cars",
                "word.balance",
                "word.withdraw",
                "sentence.change-car",
                "word.class",
                "word.car-model",
                "word.from",
                "word.to"
        );

    }

}