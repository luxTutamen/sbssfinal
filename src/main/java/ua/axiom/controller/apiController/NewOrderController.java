package ua.axiom.controller.apiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.exceptions.IllegalDataFormatException;
import ua.axiom.controller.exceptions.NotEnoughMoneyException;
import ua.axiom.model.objects.*;
import ua.axiom.repository.ClientRepository;
import ua.axiom.repository.DiscountRepository;
import ua.axiom.service.LocalisationService;
import ua.axiom.service.OrderService;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/api/neworder")
public class NewOrderController {
    private LocalisationService localisationService;
    private OrderService orderService;

    private ClientRepository clientRepository;
    private DiscountRepository discountRepository;

    @Autowired
    public NewOrderController(
            ClientRepository clientRepository,
            LocalisationService localisationService,
            DiscountRepository discountRepository,
            OrderService orderService
    ) {
        this.localisationService = localisationService;
        this.orderService = orderService;

        this.clientRepository = clientRepository;
        this.discountRepository = discountRepository;
    }

    @RequestMapping
    public ModelAndView orderRequest() {
        Map<String, Object> model = new HashMap<>();

        Long id = ((Client)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Client user = clientRepository.findById(id).get();

        fillUserSpecificContent(model, user);
        fillLocalisedTextContent(model, user.getLocale().toJavaLocale());

        return new ModelAndView("/appPages/neworder", model);
    }

    @PostMapping(name = "new-order")
    public ModelAndView postNewOrder(
            @RequestParam String departure,
            @RequestParam String destination,
            @RequestParam String aClass
    ) throws NotEnoughMoneyException, IllegalDataFormatException {
        Map<String, Object> model = new HashMap<>();

        long clientID = ((Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Client client = clientRepository.getOne(clientID);

        if(     !departure.matches(localisationService.getRegex("location", client.getLocale().toJavaLocale())) ||
                !destination.matches(localisationService.getRegex("location", client.getLocale().toJavaLocale()))) {
            throw new IllegalDataFormatException();
        }

        Order newOrder = Order.builder()
                .date(new Date())
                .departure(departure)
                .destination(destination)
                .client(client)
                .status(Order.Status.PENDING)
                .cClass(Car.Class.valueOf(aClass))
                .build();

        orderService.processNewOrder(newOrder);

        fillUserSpecificContent(model, client);
        fillLocalisedTextContent(model, client.getLocale().toJavaLocale());

        return new ModelAndView("/appPages/neworder", model);
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ModelAndView notEnoughMoneyException() {
        Map<String, Object> model = new HashMap<>();
        Client client = (Client)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.put("error", "Not enough money!");

        fillUserSpecificContent(model, client);
        fillLocalisedTextContent(model, client.getLocale().toJavaLocale());

        return new ModelAndView("/appPages/neworder", model);
    }

    @ExceptionHandler(IllegalDataFormatException.class)
    public ModelAndView illegalInputHandler() {
        Map<String, Object> model = new HashMap<>();
        Client client = (Client)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.put("error", "Illegal input format");

        fillUserSpecificContent(model, client);
        fillLocalisedTextContent(model, client.getLocale().toJavaLocale());

        return new ModelAndView("/appPages/neworder", model);
    }

    private void fillUserSpecificContent(Map<String, Object> model, Client client) {
        model.put("new-order-details", Order.getOrderInputDescriptions());
        model.put("car-classes", Car.ClassTDO.getCarClassTDOList());
        model.put("client-balance", client.getMoney());
        model.put("username", client.getUsername());
        model.put("current-locale", client.getLocale());
        model.put("promos-list", discountRepository.getByClient(client));

    }

    private void fillLocalisedTextContent(Map<String, Object> model, Locale locale) {
        localisationService.setLocalisedMessages(
                model,
                locale,
                "sentence.new-order-page-desc",
                "sentence.new-order-request-msg",
                "word.submit",
                "word.logout",
                "sentence.logged-welcome",
                "sentence.logged-as",
                "word.logout",
                "word.company-name"
        );
    }
}
