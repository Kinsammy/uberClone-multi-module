package io.samtech.service;


import io.samtech.application.event.publisher.UserEventPublisher;
import io.samtech.configuration.configurer.cloudinary.ICloudService;
import io.samtech.constants.CommonConstants;
import io.samtech.dto.request.RegisterDriverRequest;
import io.samtech.entity.Driver;
import io.samtech.entity.models.Role;
import io.samtech.entity.models.User;
import io.samtech.exception.DriverNotFoundException;
import io.samtech.exception.UserAlreadyExistByEmailException;
import io.samtech.exception.UserAlreadyExitByPhoneNumberException;
import io.samtech.repository.DriverRepository;
import io.samtech.serviceApi.DriverProfileServiceApi;
import io.samtech.serviceApi.user.UserService;
import io.samtech.utils.CopyUtils;
import io.samtech.utils.DataProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class DriverProfileServiceImpl implements DriverProfileServiceApi {
    private final DriverRepository driverRepository;
    private final UserService userService;
    private final UserEventPublisher userEventPublisher;
    private final PasswordEncoder passwordEncoder;
    private final ICloudService cloudService;
    @Override
    public void registerDriver(RegisterDriverRequest request) {
        User user = setDriverDetails(request);
        userService.saveUser(user);
        Driver driver = new Driver();
        driver.setUserDetails(user);
        saveDriverProfile(driver);

        userEventPublisher.publishVerificationEvent(driver.getUserDetails());
    }

    private User setDriverDetails(RegisterDriverRequest request) {
        if (userService.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistByEmailException();
        }
        if (userService.existsByPhoneNumber(request.getPhoneNumber())){
            throw new UserAlreadyExitByPhoneNumberException();
        }

        final String fullName = DataProcessor.joinWihSpaceDelimiter(request.getGivenName(), request.getMiddleName(), request.getFamilyName());
        return User.builder()
                .name(fullName)
                .locked(CommonConstants.EntityStatus.UNLOCKED)
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .gender(request.getGender())
                .familyName(request.getFamilyName())
                .givenName(request.getGivenName())
                .username(UUID.randomUUID().toString())
                .unsigned_name(StringUtils.stripAccents(fullName))
                .role(Role.PASSENGER)
                .rawPassword(request.getRawPassword())
                .password(passwordEncoder.encode(request.getRawPassword()))
                .enabled(CommonConstants.EntityStatus.ENABLED)
                .build();
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
