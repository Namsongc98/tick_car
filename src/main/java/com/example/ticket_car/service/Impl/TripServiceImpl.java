package com.example.ticket_car.service.Impl;

import com.example.ticket_car.entity.Trip;
import com.example.ticket_car.repository.TripRepository;
import com.example.ticket_car.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TripServiceImpl implements TripService {

    @Autowired
    TripRepository tripRepository;

    @Override
    public Page<Trip> getTripFollowPage(Pageable pageable){return tripRepository.findAll(pageable);};
}
