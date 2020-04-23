package ua.axiom.controller.apiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.exceptions.NotEnoughMoneyException;
import ua.axiom.model.objects.*;
import ua.axiom.repository.*;
import ua.axiom.service.LocalisationService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/api/neworder")
public class NewOrderController {
    private LocalisationService localisationService;

    private ClientRepository clientRepository;
    private OrderRepository orderRepository;
    private int ordersPage = 0;

    private static final Random priceGenerator = new Random();

    @Autowired
    public NewOrderController(ClientRepository clientRepository,LocalisationService localisationService, OrderRepository orderRepository) {
        this.localisationService = localisationService;

        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
    }

    @RequestMapping
    public ModelAndView orderRequest() {
        Map<String, Object> model = new HashMap<>();

        Long id = ((Client)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Client user = clientRepository.findById(id).get();

        fillUserSpecificContent(model, user);
        fillLocalisedTextContent(model, user);

        return new ModelAndView("/apiPages/neworder", model);
    }

    @PostMapping(name = "new-order")
    public ModelAndView postNewOrder(
            @RequestParam String departure,
            @RequestParam String destination,
            @RequestParam String aClass
    ) throws NotEnoughMoneyException {

        long id = ((Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Client client = clientRepository.getOne(id);
        BigDecimal price = new BigDecimal(priceGenerator.nextInt() % 500 + 500 + ".00");

        if(price.compareTo(client.getMoney()) == 1) {
            throw new NotEnoughMoneyException();
        }

        client.setMoney(client.getMoney().subtract(price));

        Order newOrder = Order.builder()
                .date(new Date())
                .departure(departure)
                .destination(destination)
                .client(client)
                .status(Order.Status.PENDING)
                .price(price)
                .cClass(Car.Class.valueOf(aClass))
                .build();

        orderRepository.save(newOrder);

        Map<String, Object> model = new HashMap<>();

        fillUserSpecificContent(model, client);
        fillLocalisedTextContent(model, client);

        return new ModelAndView("/apiPages/neworder", model);
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ModelAndView notEnoughMoneyException() {
        Map<String, Object> model = new HashMap<>();

        Client client = (Client)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.put("error", "Not enough money!");

        fillUserSpecificContent(model, client);
        fillLocalisedTextContent(model, client);

        return new ModelAndView("/apiPages/neworder", model);
    }

    private void fillUserSpecificContent(Map<String, Object> model, Client client) {
        model.put("new-order-details", Order.getOrderInputDescriptions());
        model.put("car-classes", Car.ClassTDO.getCarClassTDOList());
        model.put("client-balance", client.getMoney());
        model.put("username", client.getUsername());
        model.put("current-locale", client.getLocale());

    }

    private void fillLocalisedTextContent(Map<String, Object> model, User user) {
        localisationService.setLocalisedMessages(
                model,
                user.getLocale().toJavaLocale(),
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
