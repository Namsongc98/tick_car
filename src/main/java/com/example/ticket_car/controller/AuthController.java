package com.example.ticket_car.controller;


import com.example.ticket_car.Dto.baseResponseDto.BaseResponseDto;
import com.example.ticket_car.Dto.request.UserRequestDto;
import com.example.ticket_car.Dto.response.TokenResponse;
import com.example.ticket_car.anotation.NoAuth;
import com.example.ticket_car.service.AuthService;
import com.example.ticket_car.entity.User;

import com.example.ticket_car.util.JwtUtil;
import jakarta.validation.Valid;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    @NoAuth
    public ResponseEntity<BaseResponseDto<TokenResponse>> register(@Valid @RequestBody UserRequestDto req){
        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setPassword(req.getPassword());
        user.setRole(req.getRole());
        user.setCreatedAt(Instant.now());
        User savedUser = authService.register(req);
        String accessToken = jwtUtil.generateAccessToken(savedUser.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(savedUser.getEmail());
        TokenResponse token = new TokenResponse(accessToken, refreshToken, savedUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponseDto.success(201, "Register successfully", token));
    }

    @PostMapping("/login")
    @NoAuth
    public ResponseEntity<BaseResponseDto<TokenResponse>> login(@Valid @RequestBody UserRequestDto req){
        System.out.println(req);
        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());
        user.setCreatedAt(Instant.now());
        User loginUser = authService.login(req);
        String accessToken = jwtUtil.generateAccessToken(loginUser.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(loginUser.getEmail());
        TokenResponse token = new TokenResponse(accessToken, refreshToken, loginUser);
        return ResponseEntity.ok(BaseResponseDto.success(200,"Get successfully",token));
    }
}
