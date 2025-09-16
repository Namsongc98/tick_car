package com.example.ticket_car.controller;

import com.example.ticket_car.Dto.baseResponseDto.BaseResponseDto;
import com.example.ticket_car.Dto.request.UserProfileRequestDto;
import com.example.ticket_car.Dto.request.UserProfileResponseDto;
import com.example.ticket_car.entity.UserProfile;
import com.example.ticket_car.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
public class UserProfileController {

    private final UserProfileService profileService;

    public UserProfileController(UserProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfile> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getProfile(userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<BaseResponseDto<UserProfile>> createProfile(@PathVariable Long userId,
                                                                @RequestBody UserProfileRequestDto dto) {
        UserProfile userProfile = profileService.createProfile(userId, dto);
        return ResponseEntity.ok(BaseResponseDto.success(200, "Get successfully",userProfile));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserProfile> updateProfile(@PathVariable Long userId,
                                                                @RequestBody UserProfileRequestDto dto) {
        return ResponseEntity.ok(profileService.updateProfile(userId, dto));
    }
}
