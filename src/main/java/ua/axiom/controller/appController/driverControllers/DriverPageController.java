package ua.axiom.controller.appController.driverControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.MultiViewController;
import ua.axiom.service.error.exceptions.JustTakenException;
import ua.axiom.model.Driver;
import ua.axiom.service.GuiService;
import ua.axiom.service.appservice.DriverService;
import ua.axiom.service.appservice.OrderService;
import ua.axiom.service.error.exceptions.NotEnoughMoneyException;

import java.util.Map;

@Controller
@RequestMapping("/" + DriverPageController.VIEW)
public class DriverPageController extends MultiViewController {
    public final static String VIEW = "driverpage";

    private final DriverService driverService;
    private final OrderService orderService;

    @Autowired
    public DriverPageController(
            DriverService driverService,
            OrderService orderService,
            WithOrderDriverController withOrderDriverController,
            WithoutOrderDriverController withoutOrderDriverController
    ) {
        this.orderService = orderService;
        this.driverService = driverService;

        super.addController(() -> driverService.hasOrder(), withOrderDriverController);
        super.addController(() -> ! driverService.hasOrder(), withoutOrderDriverController);
    }

    @RequestMapping
    public ModelAndView getDriverPage(Map<String, Object> model) {
        //  guiService.populateModelWithNavbarData(model);

        return super.getRequestMapping(new ConcurrentModel(model));
    }

    @PostMapping("/takeorder")
    public String takeOrderController(@RequestParam("orderId") long orderId) throws JustTakenException {

        long driverId = ((Driver) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        driverService.takeOrder(driverId, orderId);

        return "redirect:/" + VIEW;
    }

    @PostMapping("/confirmation")
    public String confirmationPost() {

        long driverId = ((Driver) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        orderService.confirmByDriver(driverId);

        return "redirect:/" + VIEW;
    }

    @GetMapping("/withdraw")
    public String withDrawController() throws NotEnoughMoneyException {

        driverService.withdrawMoney(((Driver) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        return "redirect:/" + VIEW;
    }

}
