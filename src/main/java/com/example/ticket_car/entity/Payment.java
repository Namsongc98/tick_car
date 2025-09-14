package com.example.ticket_car.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.List;

import com.example.ticket_car.Enum.Payment.PaymentMethod;
import com.example.ticket_car.Enum.Payment.PaymentStatus;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "payment_time")
    private Instant paymentTime;

    @Column(name = "created_at")
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToOne(mappedBy = "payment")
    @JsonBackReference
    private Ticket ticket;

}
