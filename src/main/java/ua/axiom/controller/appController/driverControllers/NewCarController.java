package ua.axiom.controller.appController.driverControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.ThymeleafController;
import ua.axiom.model.Car;
import ua.axiom.model.Driver;
import ua.axiom.service.appservice.DriverService;

@Controller
@RequestMapping("/driver/newCar")
public class NewCarController extends ThymeleafController<Driver> {

    private DriverService driverService;

    @Autowired
    public NewCarController(DriverService driverService) {
        this.driverService = driverService;
    }

    @Override
    protected ModelAndView formResponse(Model model) {
        return new ModelAndView("appPages/newCar", model.asMap());
    }

    @Override
    protected void fillUserSpecificData(Model model, Driver user) { }

    @PostMapping
    public String newCarPost(@RequestParam String carModel, @RequestParam String carType) {

        long driverId = ((Driver)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Car car = new Car(carModel, Car.Class.valueOf(carType));

        driverService.saveNewCar(car, driverId);

        return "redirect:/driverpage";
    }
}
