package ua.axiom.service.appservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.axiom.model.Driver;
import ua.axiom.repository.DriverRepository;

import java.util.List;

@Service
public class DriverService {
    private DriverRepository driverRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public List<Driver> findAll(int page, int size) {
        return driverRepository.findAll(PageRequest.of(page, size)).getContent();
    }

}
