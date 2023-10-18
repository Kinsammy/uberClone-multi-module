package io.samtech.service;

import io.samtech.constants.CommonConstants;
import io.samtech.dto.request.NewPassengerRequest;
import io.samtech.entity.Passenger;
import io.samtech.entity.models.Role;
import io.samtech.entity.models.User;
import io.samtech.exception.PassengerNotFoundException;
import io.samtech.repository.PassengerRepository;
import io.samtech.serviceApi.PassengerProfileServiceApi;
import io.samtech.serviceApi.user.UserService;
import io.samtech.utils.CopyUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class PassengerServiceApiImpl implements PassengerProfileServiceApi {
    private final PassengerRepository passengerRepository;
    private final UserService userService;
    private final ModelMapper mapper;
    @Override
    public void registerPassenger(NewPassengerRequest request) {
        User user = new User();
        Passenger passengerProfile = new Passenger();

        Role passwordRole = Role.PASSENGER;
        user.setRole(passwordRole);
        user.setName(request.getGivenName());
        user.setProfile(Set.of(passengerProfile));
        user.setLocked(CommonConstants.EntityStatus.LOCKED);
        userService.createProfileTypeUser(user);
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
