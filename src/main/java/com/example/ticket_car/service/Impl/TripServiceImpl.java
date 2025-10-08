package com.example.ticket_car.service.Impl;

import com.example.ticket_car.Enum.User.Role;
import com.example.ticket_car.entity.Trip;
import com.example.ticket_car.entity.User;
import com.example.ticket_car.repository.TripRepository;
import com.example.ticket_car.repository.UserRepository;
import com.example.ticket_car.service.TripService;
import com.example.ticket_car.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
public class TripServiceImpl implements TripService {

    @Autowired
    TripRepository tripRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Page<Trip> getTripFollowPage(Pageable pageable){return tripRepository.findAll(pageable);};


    @Override
    public Trip createTrip(Trip trip, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (user.getRole() != Role.STAFF && user.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Only STAFF or ADMIN can create trips");
        }
        trip.setCreatedAt(Instant.now());
        trip.setUpdatedAt(Instant.now());
        return tripRepository.save(trip);
    }

    public Trip updateTrip(Long id, Trip updatedTrip, Long idUser) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (user.getRole() != Role.STAFF && user.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Only STAFF or ADMIN can update trips");
        }
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found"));
        trip.setRoute(updatedTrip.getRoute());
        trip.setDepartureTime(updatedTrip.getDepartureTime());
        trip.setArrivalTime(updatedTrip.getArrivalTime());
        trip.setBusType(updatedTrip.getBusType());
        trip.setTotalSeats(updatedTrip.getTotalSeats());
        trip.setAvailableSeats(updatedTrip.getAvailableSeats());
        trip.setPrice(updatedTrip.getPrice());
        trip.setStatus(updatedTrip.getStatus());
        trip.setUpdatedAt(Instant.now());
        return tripRepository.save(trip);
    }
}
