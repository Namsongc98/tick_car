package com.example.ticket_car.repository;

import com.example.ticket_car.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
    Optional<UserProfile> findById(Long Id);
    Optional<UserProfile> findByUserId(Long Id);
}
