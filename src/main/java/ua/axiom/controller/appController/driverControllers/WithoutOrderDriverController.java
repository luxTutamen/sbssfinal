package ua.axiom.controller.appController.driverControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.ThymeleafController;
import ua.axiom.model.Driver;
import ua.axiom.model.Order;
import ua.axiom.service.appservice.DriverService;
import ua.axiom.service.appservice.OrderService;

@Component
public class WithoutOrderDriverController extends ThymeleafController<Driver> {
    private static final String VIEW = "appPages/driverpages/noOrderDriverpage";

    private OrderService orderService;

    @Autowired
    public WithoutOrderDriverController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    protected ModelAndView formResponse(Model model) {
        return new ModelAndView(VIEW, model.asMap());
    }

    @Override
    protected void fillUserSpecificData(Model model, Driver user) {
        model.addAttribute("orders", orderService.findByCClassAndStatus(PageRequest.of(0, 10), user.getCar().getAClass(), Order.Status.PENDING));
        model.addAttribute("balance", user.getBalance());
    }

}