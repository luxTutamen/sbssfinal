package ua.axiom.controller.appController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.model.objects.*;
import ua.axiom.repository.ClientRepository;
import ua.axiom.repository.OrderRepository;
import ua.axiom.service.LocalisationService;
import ua.axiom.service.OrderService;
import ua.axiom.service.PromoService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/userpage")
public class ClientPageController {
    private LocalisationService localisationService;
    private OrderService orderService;
    private PromoService promoService;

    private OrderRepository orderRepository;
    private ClientRepository clientRepository;
    private int ordersPage = 0;

    @Autowired
    public ClientPageController(
            LocalisationService localisationService,
            OrderRepository orderRepository,
            ClientRepository clientRepository,
            OrderService orderService,
            PromoService promoService
    ) {
        this.localisationService = localisationService;
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.orderService = orderService;
        this.promoService = promoService;
    }

    @RequestMapping
    public ModelAndView getCabinetControllerRequest() {
        HashMap<String, Object> model = new HashMap<>();
        Long id = ((Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Client client = clientRepository.findById(id).get();

        fillPage(model, client.getLocale());
        fillUserSpecificData(model, client);

        promoService.onClientLoad(client);

        return new ModelAndView("appPages/userpage", model);
    }

    @PostMapping("/nextpage")
    public ModelAndView postNextOrderPage() {
        System.out.println("posted pagination to next page");
        ordersPage++;

        return getCabinetControllerRequest();
    }

    @PostMapping("/prevpage")
    public ModelAndView postPrevOrderPage() {
        System.out.println("posted pagination to prev page");
        if(ordersPage > 0) {
            ordersPage--;
        }

        return getCabinetControllerRequest();
    }

    @PostMapping("/confirm")
    private String confirmOrder(@RequestParam long orderId) {
        System.out.println("confirmation of order " + orderId);

        Order currentOrder = orderRepository.getOne(orderId);
        currentOrder.setConfirmedByClient(true);
        orderRepository.save(currentOrder);

        orderService.processFinishedOrder(orderId);

        return "redirect:/userpage";
    }

    @PostMapping("/cancelorder")
    private String cancelOrder(@RequestParam long orderId) {

        Order order = orderRepository.findById(orderId).get();
        order.setStatus(Order.Status.FINISHED);
        orderRepository.save(order);

        return "redirect:/userpage";
    }

    private void fillUserSpecificData(Map<String, Object> model, Client client) {
        model.put("username", client.getUsername());
        model.put("balance", client.getMoney());
        model.put("current-locale", client.getLocale());
        model.put("client-balance", client.getMoney());

        model.put("pending-orders", orderRepository.findByStatusAndClient(PageRequest.of(ordersPage, 4), Order.Status.PENDING, client));
        model.put("taken-orders", orderRepository.findByStatusAndClient(PageRequest.of(ordersPage, 4), Order.Status.TAKEN, client));
        model.put("page", ordersPage);
    }

    private void fillPage(Map<String, Object> model, UserLocale locale) {
        localisationService.setLocalisedMessages(
                model,
                locale.toJavaLocale(),
                "word.hello",
                "word.menu",
                "word.logout",
                "sentence.new-order",
                "sentence.your-balance",
                "sentence.cancel-order",
                "sentence.promocodes",
                "sentence.replenish-balance",
                "sentence.delete-account",
                "word.company-name",
                "sentence.logged-as",
                "info.username",
                "word.from",
                "word.to",
                "word.class",
                "word.fee",
                "word.cancel",
                "word.balance",
                "sentence.your-orders",
                "sentence.order-history"
        );

        model.put("order-history", orderRepository.findAll(PageRequest.of(0, 10)));
        model.put("locales",  UserLocale.getLocalesList());
        model.put("new-order-details", Order.getOrderInputDescriptions());
        model.put("car-classes", Car.ClassTDO.getCarClassTDOList());
    }
}
