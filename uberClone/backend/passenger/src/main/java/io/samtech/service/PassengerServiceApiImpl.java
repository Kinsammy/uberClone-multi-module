package io.samtech.service;

import io.samtech.application.event.publisher.UserEventPublisher;
import io.samtech.constants.CommonConstants;
import io.samtech.dto.request.CreateUserRequest;
import io.samtech.dto.request.NewPassengerRequest;
import io.samtech.entity.Passenger;
import io.samtech.entity.models.Role;
import io.samtech.entity.models.User;
import io.samtech.exception.PassengerNotFoundException;
import io.samtech.exception.UserAlreadyExistByEmailException;
import io.samtech.exception.UserAlreadyExitByPhoneNumberException;
import io.samtech.repository.PassengerRepository;
import io.samtech.serviceApi.PassengerProfileServiceApi;
import io.samtech.serviceApi.user.UserService;
import io.samtech.utils.CopyUtils;
import io.samtech.utils.DataProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class PassengerServiceApiImpl implements PassengerProfileServiceApi {
    private final PassengerRepository passengerRepository;
    private final UserService userService;
    private final UserEventPublisher userEventPublisher;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void registerPassenger(CreateUserRequest request) {
        if (userService.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistByEmailException();
        }
        if (userService.existsByPhoneNumber(request.getPhoneNumber())){
            throw new UserAlreadyExitByPhoneNumberException();
        }
        final String fullName = DataProcessor.joinWihSpaceDelimiter(request.getGivenName(), request.getMiddleName(), request.getFamilyName());
        User user = User.builder()
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
        userService.saveUser(user);
        Passenger passengerProfile = new Passenger();
        passengerProfile.setUserDetails(user);
        savePassengerProfile(passengerProfile);

        userEventPublisher.publishVerificationEvent(passengerProfile.getUserDetails());
    }

    @Override
    public Passenger savePassengerProfile(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    @Override
    public Passenger findPassengerProfileById(Long passengerProfileId) {
        return passengerRepository.findById(passengerProfileId)
                .orElseThrow(PassengerNotFoundException::new);
    }

    @Override
    public Iterable<Passenger> getAllPassengerProfiles() {
        return passengerRepository.findAll();
    }

    @Override
    public Passenger updatePassengerProfile(Passenger passenger) {
        var foundPassengerProfile = findPassengerProfileById(passenger.getId());
        CopyUtils.copyProperties(passenger, foundPassengerProfile);
        return passengerRepository.save(foundPassengerProfile);
    }

    @Override
    public void deletePassengerProfile(Passenger passenger) {
        passengerRepository.delete(passenger);
    }

    @Override
    public void deletePassengerProfileById(Long passengerProfileId) {
        passengerRepository.deleteById(passengerProfileId);
    }
}
