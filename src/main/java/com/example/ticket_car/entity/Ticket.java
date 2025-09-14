package com.example.ticket_car.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.example.ticket_car.Enum.Ticket.TicketStatus;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_number")
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Column(name = "booking_time")
    private Instant bookingTime;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    @JsonBackReference
    private Trip trip;

    @OneToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    @JsonManagedReference
    private Payment payment;

}