package com.example.ticket_car.service;

import com.example.ticket_car.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TicketService {

    Page<Ticket> getTicketFollowPage(Pageable pageable);
    Ticket bookTicket(Long userId, Long tripId, String seatNumber);
}
