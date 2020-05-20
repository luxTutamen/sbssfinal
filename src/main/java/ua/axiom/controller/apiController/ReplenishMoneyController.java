package ua.axiom.controller.apiController;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.axiom.model.objects.Client;
import ua.axiom.repository.ClientRepository;
import ua.axiom.service.apiservice.ReplenishMoneyService;

@Controller
@RequestMapping("/api/replenish")
public class ReplenishMoneyController {

    private ReplenishMoneyService replenishMoneyService;

    public ReplenishMoneyController(ReplenishMoneyService replenishMoneyService) {
        this.replenishMoneyService = replenishMoneyService;
    }

    @RequestMapping
    public String getReplenishRequest() {
        long id =  ((Client)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        replenishMoneyService.replenish(id);

        return "redirect:/";
    }
}
