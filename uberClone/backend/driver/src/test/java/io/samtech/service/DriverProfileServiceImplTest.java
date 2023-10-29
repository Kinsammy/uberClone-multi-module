package io.samtech.service;

import io.samtech.entity.Driver;
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

import java.util.List;

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

    @BeforeEach
    void setUp() {
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