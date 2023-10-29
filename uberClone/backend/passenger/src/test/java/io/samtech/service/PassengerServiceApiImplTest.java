package io.samtech.service;

import io.samtech.constants.CommonConstants;
import io.samtech.entity.Passenger;
import io.samtech.entity.models.User;
import io.samtech.repository.PassengerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PassengerServiceApiImplTest {
    @Mock
    private PassengerRepository passengerRepository;

    @InjectMocks
    private PassengerServiceApiImpl passengerService;
    private Passenger passenger;
    private Long passengerId;

    private List<Passenger> passengers;
    private User user;

    @BeforeEach
    void setUp() {
        user =  new User();
        Long userId = 1L;
        user.setId(userId);
        user.setName("Samuel");
        user.setEmail("fanusamuel@gmail.com");
        user.setRawPassword("password");
        passengerId = 1L;
        passenger = new Passenger();
        passenger.setId(passengerId);
        passenger.setUserDetails(user);


        Long passengerId2 = 2L;

        User user2 =  new User();
        Long userId2 = 2L;
        user2.setId(userId2);
        user2.setName("Ade");
        user2.setEmail("samuel@gmail.com");
        user2.setPassword("password");
        passengerId = 1L;
        Passenger passenger2 = new Passenger();
        passenger2.setId(passengerId2);
        passenger2.setUserDetails(user2);


        passengers = new ArrayList<>();
        passengers.add(passenger);
        passengers.add(passenger2);
    }

    @Test
    void registerPassenger() {
    }

    @Test
    @DisplayName("Test should be able to save Passenger Profile")
    void savePassengerProfileTest() {
        when(passengerRepository.save(passenger)).thenReturn(passenger);
        var savedPassengerProfile = passengerService.savePassengerProfile(passenger);

        assertEquals(passenger, savedPassengerProfile);
        verify(passengerRepository, times(1)).save(savedPassengerProfile);
    }

    @Test
    @DisplayName("Test should be able to get passenger profile by Id")
    void findPassengerProfileByIdTest() {
        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(passenger));
        var foundPassenger = passengerService.findPassengerProfileById(passengerId);

        assertThat(foundPassenger).isNotNull();
        assertEquals(foundPassenger.getId(), passengerId);
        verify(passengerRepository, times(1)).findById(foundPassenger.getId());
    }

    @Test
    @DisplayName("Test should be able to get all passenger's profiles")
    void getAllPassengerProfilesTest() {
        when(passengerRepository.findAll()).thenReturn(passengers);
        Iterable<Passenger> savedPassenger = passengerService.getAllPassengerProfiles();

        assertEquals(passengers, savedPassenger);
        assertEquals(passengers.size(), 2);
        verify(passengerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test should be able to update existing passenger profile")
    void updatePassengerProfileTest() {
        when(passengerRepository.save(passenger)).thenReturn(passenger);
        var savedPassenger = passengerService.savePassengerProfile(passenger);

        when(passengerRepository.findById(savedPassenger.getId())).thenReturn(Optional.of(savedPassenger));

        savedPassenger.setId(savedPassenger.getId());
        savedPassenger.setUserDetails(user);
        savedPassenger.getUserDetails().setEmailVerified(CommonConstants.EntityStatus.VERIFIED);

        var updatedPassenger = passengerService.updatePassengerProfile(savedPassenger);

        assertEquals(savedPassenger, updatedPassenger);
        assertEquals(savedPassenger.getUserDetails().getEmailVerified(),CommonConstants.EntityStatus.VERIFIED);
        verify(passengerRepository, times(2)).save(updatedPassenger);
    }

    @Test
    @DisplayName("Test should be able to delete existing passenger profile")
    void deletePassengerProfileTest() {
        when(passengerRepository.save(passenger)).thenReturn(passenger);
        var savedPassenger = passengerService.savePassengerProfile(passenger);

        when(passengerRepository.findById(savedPassenger.getId())).thenReturn(Optional.of(savedPassenger));

        passengerService.deletePassengerProfile(savedPassenger);
        savedPassenger = null;
        assertThat(savedPassenger).isNull();
        verify(passengerRepository, times(1)).delete(passenger);
    }

    @Test
    @DisplayName("Test should be able to delete existing passenger profile by Id")
    void deletePassengerProfileByIdTest() {
        when(passengerRepository.save(passenger)).thenReturn(passenger);
        var savedPassenger = passengerService.savePassengerProfile(passenger);

        when(passengerRepository.findById(savedPassenger.getId())).thenReturn(Optional.of(savedPassenger));


        passengerService.deletePassengerProfileById(savedPassenger.getId());
        savedPassenger = null;
        assertThat(savedPassenger).isNull();
        verify(passengerRepository, times(1)).deleteById(passengerId);
    }
}