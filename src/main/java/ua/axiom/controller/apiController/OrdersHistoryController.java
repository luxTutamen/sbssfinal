package ua.axiom.controller.apiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.repository.ClientRepository;
import ua.axiom.repository.OrderRepository;
import ua.axiom.service.GuiService;

import java.util.HashMap;
import java.util.Map;

//  todo
@Controller
@RequestMapping("/api/orderHistory")
public class OrdersHistoryController {
    private ClientRepository clientRepository;
    private OrderRepository orderRepository;

    private GuiService guiService;

    @Autowired
    public OrdersHistoryController(ClientRepository clientRepository, OrderRepository orderRepository, GuiService guiService) {
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
        this.guiService = guiService;
    }

    @RequestMapping
    public ModelAndView getRequest() {
        Map<String, Object> model = new HashMap<>();

        guiService.populateModelWithNavbarData(model);

        return new ModelAndView("apiPages/orderhistory", model);
    }
}
