package io.samtech.service;

import io.samtech.entity.BankInformation;
import io.samtech.entity.Driver;
import io.samtech.entity.Referee;
import io.samtech.entity.models.User;
import io.samtech.repository.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DriverProfileServiceImplTest {
    @Mock
    private DriverRepository driverRepository;
    @InjectMocks
    private DriverProfileServiceImpl driverService;
    private Driver driver;
    private List<Driver> drivers = new ArrayList<>();
    private User user;
    private final Set<Referee> referees = new HashSet<>();
    Long driverId = 1L;
    private BankInformation bankInformation;

    @BeforeEach
    void setUp() {
        user =  new User();
        Long userId = 1L;
        user.setId(userId);
        user.setName("Samuel");
        user.setEmail("fanusamuel@gmail.com");
        user.setRawPassword("password");

        driver = new Driver();
        driver.setId(driverId);
        driver.setUserDetails(user);
        driver.setLicenseNumber("RFD1234");

//        Referee setting
        Referee referee = new Referee();
        referee.setFirstName("Hannah");
        referee.setOccupation("Chef");
        referees.add(referee);

//        Account settings
        bankInformation = new BankInformation();
        bankInformation.setAccountName("Fanu Samuel");
        bankInformation.setAccountNumber("6232338828");
        bankInformation.setBankName("Fidelity Bank");

        driver.setReferees(referees);
        driver.setBankInformation(bankInformation);

        User user2 = new User();
        Long userId2 = 2L;
        user2.setId(userId2);
        user2.setName("Ade");
        user2.setEmail("samuel@gmail.com");
        user2.setPassword("password");
        Long driverId2 = 2L;

        Driver driver2 = new Driver();
        driver2.setId(driverId2);
        driver2.setUserDetails(user2);
        driver2.setLicenseNumber("NFD13e3");

        Referee referee2 = new Referee();
        referee2.setFirstName("John");
        referee2.setOccupation("Cobbler");

        referees.add(referee2);

        driver2.setReferees(referees);

        drivers.add(driver);
        drivers.add(driver2);


    }

    @Test
    void registerDriver() {
    }

    @Test
    @DisplayName("Test should be able to save Driver Profile")
    void saveDriverProfileTest() {
        when(driverRepository.save(driver)).thenReturn(driver);
        var savedDriver = driverService.saveDriverProfile(driver);

        assertEquals(driver, savedDriver);
        verify(driverRepository, times(1)).save(savedDriver);
    }

    @Test
    @DisplayName("Test should be able to get driver profile by Id")
    void findDriverProfileByIdTest() {
        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        var foundDriver = driverService.findDriverProfileById(driverId);

        assertThat(foundDriver).isNotNull();
        assertEquals(foundDriver.getId(), driverId);
        verify(driverRepository, times(1)).findById(foundDriver.getId());
    }

    @Test
    @DisplayName("Test should be able to get all drivers profiles")
    void getAllDriverProfilesTest() {
        when(driverRepository.findAll()).thenReturn(drivers);
        Iterable<Driver> savedDrivers = driverService.getAllDriverProfiles();

        assertEquals(drivers, savedDrivers);
        assertEquals(drivers.size(), 2);
        verify(driverRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test should be able to update existing driver profile")
    void updateDriverProfileTest() {
        when(driverRepository.save(driver)).thenReturn(driver);
        var savedDriver = driverService.saveDriverProfile(driver);

        when(driverRepository.findById(savedDriver.getId())).thenReturn(Optional.of(savedDriver));

        bankInformation.setBankName("Uba");
        savedDriver.setBankInformation(bankInformation);

        var updatedDriver = driverService.updateDriverProfile(savedDriver);

        assertEquals(savedDriver, updatedDriver);
        assertEquals(savedDriver.getBankInformation().getBankName(), "Uba");

        verify(driverRepository, times(2)).save(updatedDriver);
    }

    @Test
    @DisplayName("Test should be able to delete existing driver profile")
    void deleteDriverProfileTest() {
        when(driverRepository.save(driver)).thenReturn(driver);
        var savedDriver = driverService.saveDriverProfile(driver);

        when(driverRepository.findById(savedDriver.getId())).thenReturn(Optional.of(savedDriver));

        driverService.deleteDriverProfile(savedDriver);

        verify(driverRepository, times(1)).delete(savedDriver);
    }

    @Test
    void deleteDriverProfileByIdTest() {
        when(driverRepository.save(driver)).thenReturn(driver);
        var savedDriver = driverService.saveDriverProfile(driver);

        when(driverRepository.findById(savedDriver.getId())).thenReturn(Optional.of(savedDriver));

        driverService.deleteDriverProfileById(savedDriver.getId());
        verify(driverRepository, times(1)).deleteById(savedDriver.getId());
    }
}