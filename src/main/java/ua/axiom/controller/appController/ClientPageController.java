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
import ua.axiom.service.appservice.OrderService;
import ua.axiom.service.appservice.PromoService;
import ua.axiom.service.error.exceptions.JustTakenException;

@Controller
@RequestMapping("/clientpage")
public class ClientPageController extends ThymeleafController<Client> {

    private final OrderService orderService;
    private final PromoService promoService;

    //  todo refactor
    private int ordersPage = 0;

    @Autowired
    public ClientPageController(
            OrderService orderService,
            PromoService promoService
    ) {
        this.orderService = orderService;
        this.promoService = promoService;
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
        model.addAttribute("username", client.getUsername());

        model.addAttribute("pending_orders", orderService.findByStatusAndClient(PageRequest.of(ordersPage, 4), Order.Status.PENDING, client));
        model.addAttribute("taken_orders", orderService.findByStatusAndClient(PageRequest.of(ordersPage, 4), Order.Status.TAKEN, client));
        model.addAttribute("order_history", orderService.findByStatusAndClient(PageRequest.of(0, 10), Order.Status.FINISHED, client));

        model.addAttribute("page", ordersPage + 1);
        model.addAttribute("max_pages", orderService.countByClientAndStatus(client, Order.Status.PENDING));
    }

    @PostMapping("/confirm")
    private String confirmOrder(@RequestParam long orderId) {

        orderService.confirmByClient(orderId);
        return "redirect:/clientpage";
    }

    @PostMapping("/cancelorder")
    private String cancelOrder(@RequestParam long orderId) throws JustTakenException {

        orderService.cancelOrder(orderId);
        return "redirect:/clientpage";
    }

    //  pagination controllers
    @PostMapping("/nextpage")
    public ModelAndView postNextOrderPage() {
        Client client = (Client)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(ordersPage >= orderService.countByClientAndStatus(client, Order.Status.PENDING) + orderService.countByClientAndStatus(client, Order.Status.TAKEN)) {
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

}
