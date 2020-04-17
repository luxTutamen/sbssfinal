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
        System.out.println("posted new-order" + destination);

        Client user = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BigDecimal price = new BigDecimal(priceGenerator.nextInt() % 500 + 500 + ".00");

        if(price.compareTo(user.getMoney()) == 1) {
            throw new NotEnoughMoneyException();
        }

        user.setMoney(user.getMoney().subtract(price));

        Order newOrder = Order.builder()
                .date(new Date())
                .departure(departure)
                .destination(destination)
                .user(user)
                .status(Order.Status.PENDING)
                .price(price)
                .cClass(Car.Class.valueOf(aClass))
                .build();

        orderRepository.save(newOrder);

        Client client = (Client)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> model = new HashMap<>();

        fillUserSpecificContent(model, client);
        fillLocalisedTextContent(model, client);

        return new ModelAndView("/apiPages/neworder", model);
    }


    @PostMapping("/nextpage")
    public ModelAndView postNextOrderPage() {
        System.out.println("posted pagination to next page");
        ordersPage++;

        return orderRequest();
    }

    @PostMapping("/prevpage")
    public ModelAndView postPrevOrderPage() {
        System.out.println("posted pagination to prev page");
        ordersPage = (ordersPage > 0) ? ordersPage-- : ordersPage;

        return orderRequest();
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
        model.put("car-classes", Car.getCarClassTDOList());
        model.put("client-balance", client.getMoney());
        model.put("pending-orders", orderRepository.findByStatusAndUser(PageRequest.of(ordersPage, 4), Order.Status.PENDING, client));
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
