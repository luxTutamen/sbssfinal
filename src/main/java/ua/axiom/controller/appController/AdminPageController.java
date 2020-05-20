package ua.axiom.controller.appController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.MustacheController;
import ua.axiom.model.objects.Admin;
import ua.axiom.model.objects.UserLocale;
import ua.axiom.repository.AdminRepository;
import ua.axiom.repository.ClientRepository;
import ua.axiom.repository.DriverRepository;
import ua.axiom.service.GuiService;

import java.util.Map;

@Controller
@RequestMapping("/adminpage")
public class AdminPageController extends MustacheController<Admin> {
    private AdminRepository adminRepository;
    private ClientRepository clientRepository;
    private DriverRepository driverRepository;

    private GuiService guiService;

    private int clientPage = 0;
    private int driverPage = 0;
    private int adminPage = 0;

    @Autowired
    public AdminPageController(
            AdminRepository adminRepository,
            ClientRepository clientRepository,
            DriverRepository driverRepository,
            GuiService guiService
    ) {
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
        this.driverRepository = driverRepository;
        this.guiService = guiService;
    }

    @Override
    protected ModelAndView formResponse(Map<String, Object> model) {
        return new ModelAndView("/appPages/adminpage", model);
    }

    @Override
    public void fillUserSpecificData(Map<String, Object> model, Admin user) {
        model.put("clients-list", clientRepository.findAll(PageRequest.of(clientPage, 10)));
        model.put("drivers-list", driverRepository.findAll(PageRequest.of(driverPage, 10)));
        model.put("admins-list", adminRepository.findAll(PageRequest.of(adminPage, 10)));

    }

    @Override
    public void fillLocalisedPageData(Map<String, Object> model, UserLocale userLocale) {
        guiService.populateModelWithNavbarData(model);

        model.put("clientPage",  clientPage);
        model.put("driverPage", driverPage);
        model.put("adminPage", adminPage);

    }

    @PostMapping("/prevpage")
    public String prevPaginationPage(@RequestParam String what) {
        switch (what) {
            case("clientPage") : {
                if(clientPage > 0)
                    clientPage--;

                break;
            }
            case("driverPage") : {
                if(driverPage > 0)
                    driverPage--;

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

    @PostMapping("/nextpage")
    public String nextPaginationPage(@RequestParam String what) {
        switch (what) {
            case("clientPage") : {
                clientPage++;
                break;
            }
            case("driverPage") : {
                driverPage++;
                break;
            }
            case("adminPage") : {
                adminPage++;
                break;
            }
            default:{
                throw new IllegalArgumentException();
            }
        }

        return "redirect:/adminpage";
    }

}
