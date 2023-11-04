package io.samtech.repository;

import io.samtech.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findDriverByDrivingStatus(String status);
}
