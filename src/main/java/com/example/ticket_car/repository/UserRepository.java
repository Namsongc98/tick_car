package com.example.ticket_car.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ticket_car.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String Email);

    boolean existsByName(String name);
    boolean existsByEmail(String Email);

}
