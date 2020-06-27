package ua.axiom.controller.appController.driverControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.ThymeleafController;
import ua.axiom.model.Driver;
import ua.axiom.model.Order;
import ua.axiom.repository.OrderRepository;

@Component
public class WithoutOrderDriverController extends ThymeleafController<Driver> {
    private OrderRepository orderRepository;

    @Autowired
    public WithoutOrderDriverController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    protected ModelAndView formResponse(Model model) {
        return new ModelAndView("appPages/driverpages/noOrderDriverpage", model.asMap());
    }

    @Override
    protected void fillUserSpecificData(Model model, Driver user) {
        model.addAttribute("orders", orderRepository.findByCClassAndStatus(user.getCar().getAClass(), Order.Status.PENDING));
        model.addAttribute("balance", user.getBalance());
    }
}