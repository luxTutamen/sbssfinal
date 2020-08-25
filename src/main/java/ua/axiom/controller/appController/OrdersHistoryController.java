package ua.axiom.controller.appController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.MustacheController;
import ua.axiom.model.Client;
import ua.axiom.model.Order;
import ua.axiom.model.UserLocale;
import ua.axiom.service.GuiService;
import ua.axiom.service.LocalisationService;
import ua.axiom.service.appservice.OrderHistoryService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/orderHistory")
public class OrdersHistoryController extends MustacheController<Client> {
    private OrderHistoryService historyController;

    private GuiService guiService;
    private LocalisationService localisationService;

    @Autowired
    public OrdersHistoryController(
            OrderHistoryService historyController,
            GuiService guiService,
            LocalisationService localisationService
    ) {
        this.guiService = guiService;
        this.historyController = historyController;
        this.localisationService = localisationService;
    }

    @Override
    protected ModelAndView formResponse(Map<String, Object> model) {
        return new ModelAndView("apiPages/orderHistory", model);
    }

    @Override
    protected void fillUserSpecificData(Map<String, Object> model, Client user) {
        List<Order> orders = historyController.getFinishedOrder(user);
        model.put("orders", orders);

    }

    @Override
    protected void fillLocalisedPageData(Map<String, Object> model, UserLocale userLocale) {
        //`guiService.populateModelWithNavbarData(model);

        localisationService.setLocalisedMessages(
                model,
                userLocale,
                "localisationService",
                "sentence.history-page-desc"
        );
    }
}
