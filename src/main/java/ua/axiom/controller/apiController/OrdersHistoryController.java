package ua.axiom.controller.apiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.MustacheController;
import ua.axiom.model.objects.Client;
import ua.axiom.model.objects.Order;
import ua.axiom.model.objects.User;
import ua.axiom.model.objects.UserLocale;
import ua.axiom.repository.ClientRepository;
import ua.axiom.repository.OrderRepository;
import ua.axiom.service.GuiService;
import ua.axiom.service.LocalisationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//  todo
@Controller
@RequestMapping("/api/orderHistory")
public class OrdersHistoryController extends MustacheController<Client> {
    private ClientRepository clientRepository;
    private OrderRepository orderRepository;

    private GuiService guiService;
    private LocalisationService localisationService;

    @Autowired
    public OrdersHistoryController(
            ClientRepository clientRepository,
            OrderRepository orderRepository,
            GuiService guiService,
            LocalisationService localisationService
    ) {
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;

        this.guiService = guiService;
        this.localisationService = localisationService;
    }

    @Override
    protected ModelAndView formResponse(Map<String, Object> model) {
        return new ModelAndView("api/orderHistory", model);
    }

    @Override
    protected Client getPersistedUser() {
        //  todo move out (single respnsibility), here and everywhere
        long id = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return clientRepository.getOne(id);
    }

    @Override
    protected void fillUserSpecificData(Map<String, Object> model, Client user) {
        List<Order> orders = orderRepository.findByStatusAndClient(Order.Status.FINISHED, user);
        model.put("orders", orders);

    }

    @Override
    protected void fillLocalisedPageData(Map<String, Object> model, UserLocale userLocale) {
        guiService.populateModelWithNavbarData(model);
        localisationService.setLocalisedMessages(
                model,
                userLocale.toJavaLocale(),
                "localisationService"
        );
    }
}
