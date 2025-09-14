package com.example.ticket_car.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.example.ticket_car.Enum.Trip.BusType;
import com.example.ticket_car.Enum.Trip.TripStatus;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String route;

    @Column(name = "departure_time")
    private Instant departureTime;

    @Column(name = "arrival_time")
    private Instant arrivalTime;

    @Enumerated(EnumType.STRING)
    private BusType busType;

    @Column(name = "total_seats")
    private int totalSeats;

    @Column(name = "available_seats")
    private int availableSeats;

    private double price;

    @Enumerated(EnumType.STRING)
    private TripStatus status;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Ticket> tickets;

    // Getters and Setters
//    public String getId() { return id; }
//    public void setId(String id) { this.id = id; }
//    public String getRoute() { return route; }
//    public void setRoute(String route) { this.route = route; }
//    public Instant getDepartureTime() { return departureTime; }
//    public void setDepartureTime(Instant departureTime) { this.departureTime = departureTime; }
//    public Instant getArrivalTime() { return arrivalTime; }
//    public void setArrivalTime(Instant arrivalTime) { this.arrivalTime = arrivalTime; }
//    public String getBusType() { return busType; }
//    public void setBusType(String busType) { this.busType = busType; }
//    public int getTotalSeats() { return totalSeats; }
//    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }
//    public int getAvailableSeats() { return availableSeats; }
//    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
//    public double getPrice() { return price; }
//    public void setPrice(double price) { this.price = price; }
//    public String getStatus() { return status; }
//    public void setStatus(String status) { this.status = status; }
//    public Instant getCreatedAt() { return createdAt; }
//    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
//    public Instant getUpdatedAt() { return updatedAt; }
//    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
//    public List<Ticket> getTickets() { return tickets; }
//    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }
}
