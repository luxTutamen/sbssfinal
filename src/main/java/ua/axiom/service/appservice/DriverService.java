package ua.axiom.service.appservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.axiom.model.Car;
import ua.axiom.model.Driver;
import ua.axiom.repository.CarRepository;
import ua.axiom.repository.DriverRepository;

import java.util.List;

@Service
public class DriverService {
    private DriverRepository driverRepository;
    private CarRepository carRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository, CarRepository carRepository) {
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
    }

    public List<Driver> findAll(int page, int size) {
        return driverRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public void saveNewCar(Car newCar, long driverID) {

        carRepository.save(newCar);

        Driver driver = driverRepository.getOne(driverID);
        driver.setCar(newCar);
        driverRepository.save(driver);
    }

    public boolean hasOrder() {
        return driverRepository.findById(((Driver) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()).get().getCurrentOrder() != null;
    }

}
