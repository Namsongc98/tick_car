package com.example.ticket_car.controller;

import com.example.ticket_car.Dto.baseResponseDto.BaseResponseDto;
import com.example.ticket_car.entity.Trip;
import com.example.ticket_car.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
