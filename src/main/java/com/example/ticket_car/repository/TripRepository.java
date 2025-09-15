package com.example.ticket_car.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ticket_car.entity.Trip;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long> {
    Optional<Trip> findById(Long id);
}
