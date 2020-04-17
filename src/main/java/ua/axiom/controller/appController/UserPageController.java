package ua.axiom.controller.appController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.model.objects.Client;
import ua.axiom.model.objects.User;
import ua.axiom.model.objects.UserLocale;
import ua.axiom.repository.ClientRepository;
import ua.axiom.repository.OrderRepository;
import ua.axiom.service.LocalisationService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/userpage")
public class UserPageController {
    private LocalisationService localisationService;

    private OrderRepository orderRepository;
    private ClientRepository clientRepository;

    @Autowired
    public UserPageController(
            LocalisationService localisationService,
            OrderRepository orderRepository,
            ClientRepository clientRepository
    ) {
        this.localisationService = localisationService;
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
    }


    @RequestMapping
    public ModelAndView getCabinetControllerRequest() {
        HashMap<String, Object> model = new HashMap<>();
        Long id = ((Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Client client = clientRepository.findById(id).get();

        //  todo buttons to swwitch pages

        model.put("username", client.getUsername());
        model.put("order-history", orderRepository.findAll(PageRequest.of(0, 10)));
        model.put("balance", client.getMoney());
        model.put("locales",  UserLocale.getLocalesList());
        model.put("current-locale", client.getLocale());

        fillPage(model, client.getLocale());

        return new ModelAndView("appPages/userpage", model);
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
                "info.username"
        );
    }


}
