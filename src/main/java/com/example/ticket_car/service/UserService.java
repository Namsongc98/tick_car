package com.example.ticket_car.service;

import com.example.ticket_car.entity.User;

public interface UserService {
    User getUserByEmail(String email);
    User getUserById(Long id);
}
