package com.example.ticket_car.controller;

import com.example.ticket_car.Dto.baseResponseDto.BaseResponseDto;
import com.example.ticket_car.entity.Ticket;
import com.example.ticket_car.entity.User;
import com.example.ticket_car.service.TicketService;
import com.example.ticket_car.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @Autowired
    UserService userService;

    @GetMapping
    ResponseEntity<BaseResponseDto<Page<Ticket>>> getTicketFollowPage(@PageableDefault(size = 10) Pageable pageable){
        Page<Ticket> ticketPage = ticketService.getTicketFollowPage(pageable);
        return ResponseEntity.ok(BaseResponseDto.success(200, "Get successfully",ticketPage));

    }

    @PostMapping
    public ResponseEntity<BaseResponseDto<Ticket>> bookTicket(@RequestBody Map<String, String> request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // Lấy userId từ JWT
        Long tripId = Long.parseLong(request.get("tripId"));
        String seatNumber = request.get("seatNumber");
        Ticket bookTicket = ticketService.bookTicket(email, tripId, seatNumber);
        return ResponseEntity.ok(BaseResponseDto.success(200, "Post Success", bookTicket));
    }
}
