package com.example.ticket_car.controller;

import com.example.ticket_car.Dto.baseResponseDto.BaseResponseDto;
import com.example.ticket_car.anotation.NoAuth;
import com.example.ticket_car.entity.Trip;
import com.example.ticket_car.service.TripService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trip")
public class TripController {

    @Autowired
    TripService tripService;

    @GetMapping
    ResponseEntity<BaseResponseDto<Page<Trip>>> getTripFollowPage(@PageableDefault(size = 10) Pageable pageable){
        Page<Trip> tripPage = tripService.getTripFollowPage(pageable);
        return  ResponseEntity.ok(BaseResponseDto.success(200, "Get successfully",tripPage));
    }

    @PostMapping
    @NoAuth
    ResponseEntity<BaseResponseDto<Trip>> createTrip(@RequestBody Trip trip){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Trip tripResponse =tripService.createTrip(trip, email);
        return ResponseEntity.ok(BaseResponseDto.success(200, "Post Success", tripResponse));
    }

}
