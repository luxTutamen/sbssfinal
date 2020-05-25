package ua.axiom.controller.appController.driverControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.MultiViewController;
import ua.axiom.controller.exceptions.JustTakenException;
import ua.axiom.model.objects.Driver;
import ua.axiom.model.objects.Order;
import ua.axiom.repository.DriverRepository;
import ua.axiom.repository.OrderRepository;
import ua.axiom.service.GuiService;
import ua.axiom.service.LocalisationService;
import ua.axiom.service.misc.MiscNulls;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/driverpage")
public class DriverPageController extends MultiViewController {
    private GuiService guiService;
    private LocalisationService localisationService;

    private OrderRepository orderRepository;
    private DriverRepository driverRepository;

/*    private class DriverNoOrderController implements Function<Model, ModelAndView> {
        @Override
        public ModelAndView apply(Model model) {
            long id = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            Driver driver = driverRepository.findById(id).get();

            populateWithSpecificData(model.asMap(), driver);
            fillWithLocalisedMsg(model.asMap(), driver.getLocale().toJavaLocale());

            return new ModelAndView("appPages/driverpages/noOrderDriverpage", model.asMap());
        }

        private void fillWithLocalisedMsg(Map<String, Object> model, Locale locale) {
            localisationService.setLocalisedMessages(
                    model,
                    locale,
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

    private class DriverWithOrderController implements Function<Model, ModelAndView> {
        @Override
        public ModelAndView apply(Model model) {
            Driver driver = (Driver) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            populateWithSpecificData(model.asMap(), driver);
            populateWithOrderData(model.asMap(), driver);
            model.asMap().put("balance", driver.getBalance());

            return new ModelAndView("appPages/driverpages/withOrderDriverPage", model.asMap());
        }

        private void populateWithOrderData(Map<String, Object> model, Driver driver) {
            driver = driverRepository.getOne(driver.getId());
            Order order = orderRepository.getOne(driver.getCurrentOrder().getId());

            fillWithLocalisedMsg(model, driver.getLocale().toJavaLocale());

            model.put("departure", order.getDeparture());
            model.put("destination", order.getDestination());

            model.put("tax", order.getPrice());
        }

        private void fillWithLocalisedMsg(Map<String, Object> model, Locale locale) {
            localisationService.setLocalisedMessages(
                    model,
                    locale,
                    "sentence.current-order-description-msg",
                    "word.from",
                    "word.to",
                    "sentence.sentence-confirm-msg",
                    "word.balance",
                    "word.class",
                    "word.car-model"
            );
        }
    }*/

    @Autowired
    public DriverPageController(
            GuiService guiService,
            OrderRepository orderRepository,
            DriverRepository driverRepository,
            LocalisationService localisationService,
            WithOrderDriverController withOrderDriverController,
            WithoutOrderDriverController withoutOrderDriverController
    ) {
        this.guiService = guiService;
        this.orderRepository = orderRepository;
        this.driverRepository = driverRepository;
        this.localisationService = localisationService;

        super.addController(() -> driverRepository.findById(((Driver) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()).get().getCurrentOrder() != null, withOrderDriverController);
        super.addController(() -> driverRepository.findById(((Driver) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()).get().getCurrentOrder() == null, withoutOrderDriverController);
    }

    @RequestMapping
    public ModelAndView getDriverPage(Map<String, Object> model) {
        guiService.populateModelWithNavbarData(model);

        return super.getRequestMapping(new ConcurrentModel(model));

    }

    //  todo service
    @PostMapping("/takeorder")
    public ModelAndView takeOrderController(@RequestParam("orderId") long orderId) throws JustTakenException {
        Map<String, Object> model = new HashMap<>();
        guiService.populateModelWithNavbarData(model);

        long id = ((Driver) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Driver driver = driverRepository.findById(id).get();

        Optional<Order> takenOrder = orderRepository.findById(orderId);
        Order validOrder = MiscNulls.getOrThrow(takenOrder.get(), new JustTakenException());

        validOrder.setStatus(Order.Status.TAKEN);
        validOrder.setDriver(driver);
        driver.setCurrentOrder(validOrder);
        driverRepository.save(driver);

        return new ModelAndView("redirect:/driverpage", model);
    }

    //  todo service
    @PostMapping("/confirmation")
    public String confirmationPost() {
        Map<String, Object> model = new HashMap<>();
        guiService.populateModelWithNavbarData(model);

        long id = ((Driver) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        Order order = orderRepository.findByDriverAndStatus(driverRepository.getOne(id), Order.Status.TAKEN);
        order.setConfirmedByDriver(true);
        orderRepository.save(order);

        Driver driver = driverRepository.findById(id).get();
        driver.setCurrentOrder(null);
        driverRepository.save(driver);

        return "redirect:/driverpage";
    }

}

/*
{orders} done
{money}
{car}
*/