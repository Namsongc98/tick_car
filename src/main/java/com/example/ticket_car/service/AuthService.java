package com.example.ticket_car.service;


import com.example.ticket_car.Dto.request.UserRequestDto;
import com.example.ticket_car.Dto.response.TokenResponse;
import com.example.ticket_car.entity.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface AuthService  {
    User register(@Valid UserRequestDto user);
    User login(@Valid UserRequestDto user);
}
