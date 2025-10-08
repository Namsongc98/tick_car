package com.example.ticket_car.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TripResponseDto {
    private String route;
    private ZonedDateTime departureTime;
    private ZonedDateTime arrivalTime;
    private String busType;
    private Integer totalSeats;
    private Integer availableSeats;
    private Double price;
    private String status;
}
