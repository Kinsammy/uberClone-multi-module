package io.samtech.service;


import io.samtech.application.event.publisher.UserEventPublisher;
import io.samtech.dto.request.RegisterDriverRequest;
import io.samtech.entity.Driver;
import io.samtech.exception.DriverNotFoundException;
import io.samtech.repository.DriverRepository;
import io.samtech.serviceApi.DriverProfileServiceApi;
import io.samtech.serviceApi.user.UserService;
import io.samtech.utils.CopyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class DriverProfileServiceImpl implements DriverProfileServiceApi {
    private final DriverRepository driverRepository;
    private final UserService userService;
    private UserEventPublisher userEventPublisher;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void registerDriver(RegisterDriverRequest request) {

    }

    @Override
    public Driver saveDriverProfile(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public Driver findDriverProfileById(Long driverId) {
        return driverRepository.findById(driverId)
                .orElseThrow(DriverNotFoundException::new);
    }

    @Override
    public Iterable<Driver> getAllDriverProfiles() {
        return driverRepository.findAll();
    }

    @Override
    public Driver updateDriverProfile(Driver driver) {
        var foundDriver = findDriverProfileById(driver.getId());
        CopyUtils.copyProperties(driver, foundDriver);
        return driverRepository.save(foundDriver);
    }

    @Override
    public void deleteDriverProfile(Driver driver) {
        driverRepository.delete(driver);
    }

    @Override
    public void deleteDriverProfileById(Long driverId) {
        driverRepository.deleteById(driverId);
    }
}
