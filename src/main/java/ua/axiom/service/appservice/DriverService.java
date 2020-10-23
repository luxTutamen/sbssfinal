package ua.axiom.service.appservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.axiom.model.Car;
import ua.axiom.model.Driver;
import ua.axiom.model.Order;
import ua.axiom.repository.CarRepository;
import ua.axiom.repository.DriverRepository;
import ua.axiom.repository.OrderRepository;
import ua.axiom.service.error.exceptions.JustTakenException;
import ua.axiom.service.error.exceptions.NotEnoughMoneyException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class DriverService {
    private static final BigDecimal BD_THOUSAND = new BigDecimal("1000.");

    private DriverRepository driverRepository;
    private OrderRepository orderRepository;
    private CarRepository carRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository, OrderRepository orderRepository, CarRepository carRepository) {
        this.driverRepository = driverRepository;
        this.orderRepository = orderRepository;
        this.carRepository = carRepository;
    }

    public List<Driver> findAll(int page, int size) {
        return driverRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Transactional(rollbackOn = {JustTakenException.class})
    public void takeOrder(long driverId, long orderId) throws JustTakenException {

        Driver driver = driverRepository.findById(driverId).get();

        Order validOrder = orderRepository.findByIdAndStatus(orderId, Order.Status.PENDING).orElseThrow(JustTakenException::new);

        validOrder.setStatus(Order.Status.TAKEN);
        validOrder.setDriver(driver);

        driver.setCurrentOrder(validOrder);
        driverRepository.save(driver);
    }

    @Transactional
    public void saveNewCar(Car newCar, long driverID) {

        carRepository.save(newCar);

        Driver driver = driverRepository.getOne(driverID);
        driver.setCar(newCar);
        driverRepository.save(driver);
    }

    @Transactional(rollbackOn = {NotEnoughMoneyException.class})
    public void withdrawMoney(long driverID) throws NotEnoughMoneyException {
        Driver driver = driverRepository.getOne(driverID);

        if(driver.getBalance().compareTo(BD_THOUSAND) < 0) {
            throw new NotEnoughMoneyException();
        }

        driver.setBalance(driver.getBalance().subtract(BD_THOUSAND));

        driverRepository.save(driver);
    }

    public boolean hasOrder() {
        return driverRepository.findById(((Driver) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()).get().getCurrentOrder() != null;
    }
}
