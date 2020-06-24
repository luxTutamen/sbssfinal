package ua.axiom.controller.appController.driverControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.ThymeleafController;
import ua.axiom.model.Driver;
import ua.axiom.repository.OrderRepository;
import ua.axiom.service.LocalisationService;

@Component
public class WithOrderDriverController extends ThymeleafController<Driver> {
    private OrderRepository orderRepository;
    private LocalisationService localisationService;

    @Autowired
    public WithOrderDriverController(
            OrderRepository orderRepository,
            LocalisationService localisationService
    ) {
        this.orderRepository = orderRepository;
        this.localisationService = localisationService;
    }

    @Override
    protected ModelAndView formResponse(Model model) {
        return new ModelAndView("appPages/driverpages/withOrderDriverPage", model.asMap());
    }

    @Override
    protected void fillUserSpecificData(Model model, Driver user) {
        model.addAttribute("balance", user.getBalance());
        model.addAttribute("car", user.getCar());

        model.addAttribute("order", orderRepository.getOne(user.getCurrentOrder().getId()));
    }
}