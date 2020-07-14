package ua.axiom.controller.appController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.ThymeleafController;
import ua.axiom.model.*;
import ua.axiom.service.LocalisationService;
import ua.axiom.service.appservice.ClientService;
import ua.axiom.service.appservice.DiscountService;
import ua.axiom.service.appservice.OrderService;
import ua.axiom.service.error.exceptions.IllegalDataFormatException;
import ua.axiom.service.error.exceptions.NotEnoughMoneyException;

import java.util.Date;

@Controller
@RequestMapping("/api/neworder/**")
public class NewOrderController extends ThymeleafController<Client> {

    private OrderService orderService;
    private ClientService clientService;
    private DiscountService discountService;

    private LocalisationService localisationService;

    private Discount discountToUse;

    @Autowired
    public NewOrderController(
            LocalisationService localisationService,
            OrderService orderService,
            DiscountService discountService,
            ClientService clientService
    ) {
        this.localisationService = localisationService;

        this.orderService = orderService;
        this.clientService = clientService;
        this.discountService = discountService;
    }

    @PostMapping("/order")
    public String postNewOrder(
            @RequestParam String departure,
            @RequestParam String destination,
            @RequestParam String aClass
    ) throws NotEnoughMoneyException, IllegalDataFormatException {

        long clientID = ((Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        if (!departure.matches(localisationService.getRegex("location", UserLocale.getContextLocale())) ||
                !destination.matches(localisationService.getRegex("location", UserLocale.getContextLocale()))) {
            throw new IllegalDataFormatException();
        }

        Client client = clientService.findById(clientID).get();

        Order newOrder = Order.builder()
                .date(new Date())
                .departure(departure)
                .destination(destination)
                .client(client)
                .discount(discountToUse)
                .status(Order.Status.PENDING)
                .cClass(Car.Class.valueOf(aClass))
                .build();

        orderService.processNewOrder(newOrder);

        return "redirect:/clientpage";
    }

    @PostMapping("/discount")
    public String discountPost(@RequestParam long discountID) {

        this.discountToUse = discountService.findOne(discountID);
        return "redirect:/clientpage";
    }

    @Override
    protected ModelAndView formResponse(Model model) {
        return new ModelAndView("appPages/neworder", model.asMap());
    }

    @Override
    protected void fillUserSpecificData(Model model, Client client) {
        model.addAttribute("new_order_details", Order.getOrderInputDescriptions());
        model.addAttribute("car_classes", Car.Class.values());
        model.addAttribute("client_balance", client.getMoney());
        model.addAttribute("username", client.getUsername());
        model.addAttribute("promos_list", discountService.getAllDiscountsForClient(client));
    }

    @ExceptionHandler({NotEnoughMoneyException.class, IllegalDataFormatException.class})
    public ModelAndView notEnoughMoneyException(NotEnoughMoneyException neme, Model model) {

        model.addAttribute("error", neme.getShortMessage(localisationService));

        return serveRequest(new ConcurrentModel(model));
    }
}
