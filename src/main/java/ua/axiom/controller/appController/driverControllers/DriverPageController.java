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
@RequestMapping("/driverpage")
public class DriverPageController extends MultiViewController {
    private GuiService guiService;
    private DriverService driverService;
    private OrderService orderService;

    @Autowired
    public DriverPageController(
            GuiService guiService,
            DriverService driverService,
            OrderService orderService,
            WithOrderDriverController withOrderDriverController,
            WithoutOrderDriverController withoutOrderDriverController
    ) {
        this.guiService = guiService;
        this.orderService = orderService;
        this.driverService = driverService;

        super.addController(() -> driverService.hasOrder(), withOrderDriverController);
        super.addController(() -> ! driverService.hasOrder(), withoutOrderDriverController);
    }

    @RequestMapping
    public ModelAndView getDriverPage(Map<String, Object> model) {
        guiService.populateModelWithNavbarData(model);

        return super.getRequestMapping(new ConcurrentModel(model));
    }

    @PostMapping("/takeorder")
    public String takeOrderController(@RequestParam("orderId") long orderId) throws JustTakenException {

        long driverId = ((Driver) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        orderService.takeOrder(driverId, orderId);

        return "redirect:/driverpage";
    }

    //  todo ua.axiom.service
    @PostMapping("/confirmation")
    public String confirmationPost() {

        long driverId = ((Driver) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        orderService.confirmByDriver(driverId);

        return "redirect:/driverpage";
    }

    @GetMapping("/withdraw")
    public String withDrawController() throws NotEnoughMoneyException {
        driverService.withdrawMoney(((Driver) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        return "redirect:/" + "driverpage";
    }

}
