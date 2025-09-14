package com.example.ticket_car.repository;

import com.example.ticket_car.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.net.ContentHandler;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Page<Ticket> findByTripId(Long tripId, Pageable pageable);
    Page<Ticket> findByUserId(Long userId, Pageable pageable);
}
