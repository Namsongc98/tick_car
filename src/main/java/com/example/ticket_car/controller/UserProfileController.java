package com.example.ticket_car.controller;

import com.example.ticket_car.Dto.baseResponseDto.BaseResponseDto;
import com.example.ticket_car.Dto.request.UserProfileRequestDto;
import com.example.ticket_car.entity.UserProfile;
import com.example.ticket_car.entity.User;
import com.example.ticket_car.service.AvatarService;
import com.example.ticket_car.service.UserProfileService;
import com.example.ticket_car.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profiles")
public class UserProfileController {

    private final UserProfileService profileService;
    private final UserService userService;
    private final AvatarService avatarService;


    public UserProfileController(UserProfileService profileService, AvatarService avatarService, UserService userService) {
        this.profileService = profileService;
        this.avatarService = avatarService;
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfile> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getProfile(userId));
    }

    @PostMapping("/{userId}/create-profile")
    public ResponseEntity<BaseResponseDto<UserProfile>> createProfile(@PathVariable Long userId,
                                                                @RequestBody UserProfileRequestDto dto) {
        UserProfile userProfile = profileService.createProfile(userId, dto);
        return ResponseEntity.ok(BaseResponseDto.success(200, "Get successfully",userProfile));
    }

    @PutMapping("/{userId}/update-profile")
    public ResponseEntity<UserProfile> updateProfile(@PathVariable Long userId,
                                                                @RequestBody UserProfileRequestDto dto) {
        return ResponseEntity.ok(profileService.updateProfile(userId, dto));
    }

    @PostMapping("/{userId}/upload-avatar")
    public ResponseEntity<?> uploadAvatar(@PathVariable Long userId,@RequestParam("file") MultipartFile file) {
        try {
            String avatarUrl = avatarService.uploadAvatar(file);
            UserProfile profile = profileService.saveOrUpdateAvatar(userId, avatarUrl);

            return ResponseEntity.ok(BaseResponseDto.success(200, "Get successfully",profile));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        }
    }

    @PostMapping("/{userId}/upload-avatar/save-static")
    public ResponseEntity<?> uploadAvatarStatic(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        try {
            User userProfile = userService.getUserById(userId);
            if (userProfile == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(BaseResponseDto.error(404, "User not found"));
            }
            String avatarUrl = avatarService.uploadAvatarStatic(userId,file);
            UserProfile profile = profileService.saveOrUpdateAvatar(userId, avatarUrl);

            return ResponseEntity.ok(BaseResponseDto.success(200, "Get successfully",profile));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
