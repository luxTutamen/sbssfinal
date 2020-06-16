package ua.axiom.controller.appController.driverControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.model.Car;
import ua.axiom.model.Driver;
import ua.axiom.repository.CarRepository;
import ua.axiom.repository.DriverRepository;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/driver/newCar")
public class NewCarController {

    private CarRepository carRepository;
    private DriverRepository driverRepository;

    @Autowired
    public NewCarController(CarRepository carRepository, DriverRepository driverRepository) {
        this.carRepository = carRepository;
        this.driverRepository = driverRepository;
    }

    @RequestMapping
    public ModelAndView newCarRequest() {
        Map<String, Object> model = new HashMap<>();

        return new ModelAndView("appPages/newCar", model);
    }

    @PostMapping
    public String newCarPost(@RequestParam String carModel, @RequestParam String carType) {

        long id = ((Driver)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        Car car = new Car(carModel, Car.Class.valueOf(carType));
        carRepository.save(car);

        Driver driver = driverRepository.getOne(id);
        driver.setCar(car);
        driverRepository.save(driver);

        return "redirect:/driverpage";
    }
}
