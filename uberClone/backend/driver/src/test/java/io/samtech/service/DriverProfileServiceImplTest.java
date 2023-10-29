package io.samtech.service;

import io.samtech.entity.BankInformation;
import io.samtech.entity.Driver;
import io.samtech.entity.Referee;
import io.samtech.entity.models.User;
import io.samtech.repository.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DriverProfileServiceImplTest {
    @Mock
    private DriverRepository driverRepository;
    @InjectMocks
    private DriverProfileServiceImpl driverService;
    private Driver driver;
    private Long driverId;
    private List<Driver> drivers;
    private User user;
    private Set<Referee> referees = new HashSet<>();
    private BankInformation bankInformation;

    @BeforeEach
    void setUp() {
        user =  new User();
        Long userId = 1L;
        user.setId(userId);
        user.setName("Samuel");
        user.setEmail("fanusamuel@gmail.com");
        user.setRawPassword("password");

        Long driverId = 1L;
        driver = new Driver();
        driver.setId(driverId);
        driver.setUserDetails(user);
        Referee referee = new Referee();
        referee.setFirstName("Hannah");
        referee.setOccupation("Chef");

        referees.add(referee);

        driver.setReferees(referees);
    }

    @Test
    void registerDriver() {
    }

    @Test
    void saveDriverProfile() {
    }

    @Test
    void findDriverProfileById() {
    }

    @Test
    void getAllDriverProfiles() {
    }

    @Test
    void updateDriverProfile() {
    }

    @Test
    void deleteDriverProfile() {
    }

    @Test
    void deleteDriverProfileById() {
    }
}