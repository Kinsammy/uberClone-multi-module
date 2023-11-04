package io.samtech.service;

import io.samtech.constants.CommonConstants;
import io.samtech.dto.RideResponse;
import io.samtech.dto.request.RideRequest;
import io.samtech.entity.Ride;
import io.samtech.exception.RideException;
import io.samtech.exception.RideNotFoundException;
import io.samtech.repository.RideRepository;
import io.samtech.serviceApi.DriverProfileServiceApi;
import io.samtech.serviceApi.PassengerProfileServiceApi;
import io.samtech.serviceApi.RideServiceApi;
import io.samtech.utils.CopyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class RideServiceImp implements RideServiceApi {
    private final RideRepository rideRepository;
    private final PassengerProfileServiceApi passengerService;
    private final DriverProfileServiceApi driverService;
    @Override
    public RideResponse bookRide(RideRequest request) {
        var foundPassenger = passengerService.findPassengerProfileById(request.getPassengerId());

        if (foundPassenger == null) {
            throw new RideException(String.format("Passenger with id %d not found",request.getPassengerId() ));
        }


        var ride = Ride.builder()
                .passenger(foundPassenger)
                .origin(request.getOrigin())
                .destination(request.getDestination())
                .pickUpTime(request.getPickUpTime())
                .dropOffTime(request.getDropOffTime())
                .rideStatus(CommonConstants.RideStatus.REQUESTED)
                .build();

       rideRepository.save(ride);
       return getRideResponse("Ride booked successfully.");
    }

    @Override
    public RideResponse cancelRide(Long rideId) {
        var ride = findRideById(rideId);

        if (ride == null) {
            throw new RideException("Ride not found.");
        }
        ride.setDestination(CommonConstants.RideStatus.RIDE_CANCELED);
        deleteRide(ride);
        return getRideResponse("Ride canceled successfully.");
    }

    private RideResponse getRideResponse(String message) {
        var response = new RideResponse();
        response.setMessage(message);
        return response;
    }

    @Override
    public Ride saveRide(Ride ride) {
        return rideRepository.save(ride);
    }

    @Override
    public Ride findRideById(Long rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(RideNotFoundException::new);
    }

    @Override
    public Iterable<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    @Override
    public Ride updateRide(Ride ride) {
        var foundRide = findRideById(ride.getId());
        CopyUtils.copyProperties(ride, foundRide);
        return rideRepository.save(foundRide);
    }

    @Override
    public void deleteRide(Ride ride) {
        rideRepository.delete(ride);
    }

    @Override
    public void deleteRideById(Long rideId) {
        rideRepository.deleteById(rideId);
    }
}
