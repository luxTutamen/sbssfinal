package ua.axiom.controller.apiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.model.objects.Car;
import ua.axiom.model.objects.Client;
import ua.axiom.model.objects.Order;
import ua.axiom.model.objects.User;
import ua.axiom.repository.OrderRepository;
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
    private static Random priceGenerator = new Random();

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    public NewOrderController(LocalisationService localisationService) {
        this.localisationService = localisationService;
    }

    @RequestMapping
    public ModelAndView orderRequest() {
        Map<String, Object> model = new HashMap<>();

        Client user = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        fillUserSpecificContent(model, user);
        fillLocalisedTextContent(model, user);

        return new ModelAndView("/apiPages/neworder", model);
    }


    @PostMapping
    public ModelAndView postNewOrder(@RequestParam String departure, @RequestParam String destination, @RequestParam String aClass) {
        System.out.println("posted " + destination);

        Client user = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Order newOrder = Order.builder()
                .date(new Date())
                .departure(departure)
                .destination(destination)
                .user(user)
                .status(Order.Status.PENDING)
                .price(new BigDecimal(priceGenerator.nextInt() % 1000 + ".00"))
                .cClass(Car.Class.valueOf(aClass))
                .build();

        orderRepository.save(newOrder);

        Client client = (Client)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> model = new HashMap<>();

        fillUserSpecificContent(model, client);
        fillLocalisedTextContent(model, client);

        return new ModelAndView("/apiPages/neworder", model);
    }

    private void fillUserSpecificContent(Map<String, Object> model, Client client) {
        model.put("new-order-details", Order.getOrderInputDescriptions());
        model.put("car-classes", Car.getCarClassTDOList());
        model.put("client-balance", client.getMoney());
        model.put("pending-orders", orderRepository.findByStatus(Order.Status.PENDING));
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
