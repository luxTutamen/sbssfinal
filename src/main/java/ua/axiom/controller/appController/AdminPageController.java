package ua.axiom.controller.appController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.MustacheController;
import ua.axiom.controller.ThymeleafController;
import ua.axiom.model.Admin;
import ua.axiom.model.Role;
import ua.axiom.model.UserLocale;
import ua.axiom.service.GuiService;
import ua.axiom.service.appservice.AdminService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/adminpage")
public class AdminPageController extends ThymeleafController<Admin> {

    private GuiService guiService;
    private AdminService adminService;

    private Map<Role, Integer> pageInfo = new HashMap<>();

    @Autowired
    public AdminPageController(
            AdminService adminService,
            GuiService guiService
    ) {
        this.guiService = guiService;
        this.adminService = adminService;

        for (Role role : Role.values()) {
            pageInfo.put(role, 0);
        }
    }

    @Override
    protected ModelAndView formResponse(Model model) {
        model.addAttribute("clientPage",  pageInfo.get(Role.CLIENT) + 1);
        model.addAttribute("driverPage", pageInfo.get(Role.DRIVER) + 1);
        model.addAttribute("adminPage", pageInfo.get(Role.ADMIN) + 1);

        model.addAttribute("clients_list", adminService.getClients(pageInfo.get(Role.CLIENT), 10));
        model.addAttribute("drivers_list", adminService.getDrivers(pageInfo.get(Role.DRIVER), 10));
        model.addAttribute("admins_list", adminService.getAdmins(pageInfo.get(Role.ADMIN), 10));

        return new ModelAndView("/appPages/adminpage", model.asMap());
    }

    @Override
    public void fillUserSpecificData(Model model, Admin user) { }

    @PostMapping("/ban")
    protected String banController(@RequestParam long idToBan) {
        //  todo ban
        return "redirect:/adminpage";
    }

    @PostMapping("/prevpage")
    public String prevPaginationPage(@RequestParam Role role) {
        Integer currentPage = pageInfo.get(role);

        if(currentPage <= 0) {
            return "redirect:/adminpage";
        }

        pageInfo.put(role, currentPage - 1);

        return "redirect:/adminpage";

    }

    @PostMapping("/nextpage")
    public String nextPaginationPage(@RequestParam Role role) {
        Integer currentPage = pageInfo.get(role);

       /* if(currentPage < 0) {
            return "redirect:/adminpage";
        }*/

        //  todo upper limet
        pageInfo.put(role, currentPage + 1);

        return "redirect:/adminpage";
    }

}
