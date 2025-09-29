package com.example.ticket_car.service;

import com.example.ticket_car.Enum.Ticket.TicketStatus;
import com.example.ticket_car.Enum.User.Role;
import com.example.ticket_car.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TicketService {

    Page<Ticket> getTicketFollowPage(Pageable pageable);
    Ticket bookTicket(Long userId, Long tripId, String seatNumber);

    Page<Ticket> getTicketsByTripId(Long tripId, Pageable pageable, Long useId, Role role);

    Ticket updateTicket(Long id, String seatNumber, TicketStatus status, Long userId,Role role);
}
