package ua.axiom.controller.appController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.axiom.model.objects.User;
import ua.axiom.repository.AdminRepository;
import ua.axiom.repository.ClientRepository;
import ua.axiom.repository.DriverRepository;
import ua.axiom.repository.UserRepository;
import ua.axiom.service.GuiService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/adminpage")
public class AdminPageController {
    private UserRepository userRepository;
    private AdminRepository adminRepository;
    private ClientRepository clientRepository;
    private DriverRepository driverRepository;

    private GuiService guiService;

    private int clientPage = 0;
    private int driverPage = 0;
    private int adminPage = 0;

    @Autowired
    public AdminPageController(
            UserRepository userRepository,
            AdminRepository adminRepository,
            ClientRepository clientRepository,
            DriverRepository driverRepository,
            GuiService guiService
    ) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
        this.driverRepository = driverRepository;
        this.guiService = guiService;
    }

    @RequestMapping
    public String getAdminPageControllerMapping(Map<String, Object> model) {

        guiService.populateModelWithNavbarData(model);

        model.put("clients-list", clientRepository.findAll(PageRequest.of(clientPage, 10)));
        model.put("drivers-list", driverRepository.findAll(PageRequest.of(driverPage, 10)));
        model.put("admins-list", adminRepository.findAll(PageRequest.of(adminPage, 10)));

        model.put("clientPage",  clientPage);
        model.put("driverPage", driverPage);
        model.put("adminPage", adminPage);

        return "/appPages/adminpage";
    }

    @PostMapping("/ban")
    public String banMapping(@RequestParam long bannedId) {
        User user = userRepository.getOne(bannedId);
        user.setBanned(true);
        userRepository.save(user);

        return "redirect:/adminpage";
    }

    @PostMapping("/prevpage")
    public String nextPaginationPage(@RequestParam String what) {
        switch (what) {
            case("clientPage") : {
                if(clientPage > 0)
                    clientPage--;

                break;
            }
            case("driverPage") : {
                if(driverPage > 0)
                    clientPage--;

                break;
            }
            case("adminPage") : {
                if(adminPage > 0)
                    adminPage--;

                break;
            }
            default:{
                throw new IllegalArgumentException();
            }
        }

        return "redirect:/adminpage";
    }

    /*
    @PostMapping
    public void postBlockUser(@RequestParam String username) {
        System.out.println("block user: " + username);
    }

     */
}
