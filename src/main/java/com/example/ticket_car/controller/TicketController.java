package com.example.ticket_car.controller;

import com.example.ticket_car.Dto.baseResponseDto.BaseResponseDto;
import com.example.ticket_car.Enum.Ticket.TicketStatus;
import com.example.ticket_car.Enum.User.Role;
import com.example.ticket_car.entity.Ticket;
import com.example.ticket_car.entity.User;
import com.example.ticket_car.service.TicketService;
import com.example.ticket_car.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
        String userId = auth.getName(); // Lấy userId từ JWT
        Long tripId = Long.parseLong(request.get("tripId"));
        String seatNumber = request.get("seatNumber");
        Ticket bookTicket = ticketService.bookTicket(Long.parseLong(userId) , tripId, seatNumber);
        return ResponseEntity.ok(BaseResponseDto.success(200, "Post Success", bookTicket));
    }

    @GetMapping("/trip")
    public ResponseEntity<BaseResponseDto<Page<Ticket>>> getTicketsByTripId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long tripId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        Role role = Role.valueOf(auth.getAuthorities().iterator().next().getAuthority());
        Pageable pageable = PageRequest.of(page, size);
        Page<Ticket> bookTicket =  ticketService.getTicketsByTripId(tripId, pageable,Long.parseLong(userId) , role);
        return ResponseEntity.ok(BaseResponseDto.success(200, "Get Success", bookTicket));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        Role role = Role.valueOf(auth.getAuthorities().iterator().next().getAuthority());
        String seatNumber = request.get("seatNumber");
        TicketStatus status = request.containsKey("status") ? TicketStatus.valueOf(request.get("status")) : null;
        return ResponseEntity.ok(ticketService.updateTicket(id, seatNumber, status, Long.parseLong(userId), role));
    }
}
