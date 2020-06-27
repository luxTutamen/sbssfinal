package ua.axiom.controller.appController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.ThymeleafController;
import ua.axiom.model.Client;
import ua.axiom.model.Order;
import ua.axiom.model.UserLocale;
import ua.axiom.repository.OrderRepository;
import ua.axiom.service.GuiService;
import ua.axiom.service.LocalisationService;
import ua.axiom.service.appservice.OrderService;
import ua.axiom.service.appservice.PromoService;

import java.util.HashMap;
import java.util.Map;

//  todo discount use
//  todo thymeleaf use

@Controller
@RequestMapping("/clientpage")
public class ClientPageController extends ThymeleafController<Client> {
    private LocalisationService localisationService;
    private GuiService guiService;

    private OrderService orderService;
    private PromoService promoService;

    private OrderRepository orderRepository;
    private int ordersPage = 0;

    @Autowired
    public ClientPageController(
            LocalisationService localisationService,
            OrderRepository orderRepository,
            OrderService orderService,
            PromoService promoService,
            GuiService guiService
    ) {
        this.localisationService = localisationService;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.promoService = promoService;
        this.guiService = guiService;
    }

    @Override
    protected ModelAndView formResponse(Model model) {
        return new ModelAndView("appPages/clientpage", model.asMap());
    }

    @Override
    public void processRequest(Client client) {
        promoService.onClientLoad(client);
    }

    @Override
    protected void fillUserSpecificData(Model model, Client client) {
        model.addAttribute("balance", client.getMoney());

        //  todo move outside
        model.addAttribute("username", client.getUsername());

        model.addAttribute("client_balance", client.getMoney());

        model.addAttribute("pending_orders", orderRepository.findByStatusAndClient(PageRequest.of(ordersPage, 4), Order.Status.PENDING, client));
        model.addAttribute("taken_orders", orderRepository.findByStatusAndClient(PageRequest.of(ordersPage, 4), Order.Status.TAKEN, client));

        model.addAttribute("order_history", orderRepository.findByStatusAndClient(PageRequest.of(0, 10), Order.Status.FINISHED, client));
        //  model.put("new-order-details", Order.getOrderInputDescriptions());

        model.addAttribute("page", ordersPage + 1);
        model.addAttribute("max_pages", orderRepository.countByClientAndStatus(client, Order.Status.PENDING));
    }

    //  pagination controllers
    @PostMapping("/nextpage")
    public ModelAndView postNextOrderPage() {
        Client client = (Client)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(ordersPage >= orderRepository.countByClientAndStatus(client, Order.Status.PENDING) + orderRepository.countByClientAndStatus(client, Order.Status.TAKEN)) {
            ordersPage++;
        }

        return formResponse(new ConcurrentModel());
    }

    @PostMapping("/prevpage")
    public ModelAndView postPrevOrderPage() {
        System.out.println("posted pagination to prev page");
        if(ordersPage > 0) {
            ordersPage--;
        }

        return formResponse(new ConcurrentModel());
    }

    @PostMapping("/confirm")
    private String confirmOrder(@RequestParam long orderId) {
        System.out.println("confirmation of order " + orderId);

        Order currentOrder = orderRepository.getOne(orderId);
        currentOrder.setConfirmedByClient(true);
        orderRepository.save(currentOrder);

        orderService.processFinishedOrder(orderId);

        return "redirect:/clientpage";
    }

    @PostMapping("/cancelorder")
    private String cancelOrder(@RequestParam long orderId) {

        Order order = orderRepository.findById(orderId).get();
        order.setStatus(Order.Status.FINISHED);
        orderRepository.save(order);

        return "redirect:/clientpage";
    }

}
