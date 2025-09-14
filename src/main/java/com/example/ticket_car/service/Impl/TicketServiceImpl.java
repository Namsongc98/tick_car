package com.example.ticket_car.service.Impl;

import com.example.ticket_car.Enum.Ticket.TicketStatus;
import com.example.ticket_car.Enum.User.Role;
import com.example.ticket_car.entity.Trip;
import com.example.ticket_car.entity.User;
import com.example.ticket_car.repository.TicketRepository;
import com.example.ticket_car.repository.TripRepository;
import com.example.ticket_car.repository.UserRepository;
import com.example.ticket_car.repository.PaymentRepository;
import com.example.ticket_car.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.example.ticket_car.entity.Ticket;

import java.time.Instant;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Page<Ticket> getTicketFollowPage(Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }

    @Override
    public Ticket bookTicket(Long userId, Long tripId, String seatNumber) {
    // Kiểm tra user tồn tại và có role CUSTOMER
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (user.getRole() != Role.CUSTOMER) {
            throw new AccessDeniedException("Only CUSTOMER can book tickets");
        }

        // Kiểm tra trip tồn tại và ghế còn trống
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found"));
        if (trip.getAvailableSeats() <= 0) {
            throw new IllegalStateException("No available seats");
        }
        // Kiểm tra ghế đã được đặt chưa
        if (ticketRepository.findByTripId(tripId, Pageable.unpaged()).getContent()
                .stream().anyMatch(t -> t.getSeatNumber().equals(seatNumber))) {
            throw new IllegalStateException("Seat already booked");
        }

        // Tạo ticket
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setTrip(trip);
        ticket.setSeatNumber(seatNumber);
        ticket.setStatus(TicketStatus.BOOKED);
        ticket.setBookingTime(Instant.now());
        ticket.setCreatedAt(Instant.now());
        ticket.setUpdatedAt(Instant.now());

        // Cập nhật số ghế còn trống
        trip.setAvailableSeats(trip.getAvailableSeats() - 1);
        tripRepository.save(trip);

        return ticketRepository.save(ticket);
    }
}
