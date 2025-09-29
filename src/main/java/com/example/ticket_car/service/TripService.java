package com.example.ticket_car.service;

import com.example.ticket_car.entity.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TripService {

    Page<Trip> getTripFollowPage(Pageable pageable);

    Trip createTrip(Trip trip, Long id);

    Trip updateTrip(Long id, Trip updatedTrip, Long idUser);
}
