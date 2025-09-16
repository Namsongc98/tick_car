package com.example.ticket_car.service;

import com.example.ticket_car.Dto.request.UserProfileRequestDto;
import com.example.ticket_car.entity.User;
import com.example.ticket_car.entity.UserProfile;
import com.example.ticket_car.repository.UserProfileRepository;
import com.example.ticket_car.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@AllArgsConstructor
public class UserProfileService {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    UserRepository userRepository;

    public UserProfile createProfile(Long userId, UserProfileRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = UserProfile.builder()
                .user(user)
                .phone(dto.getPhone())
                .name(dto.getName())
                .avatar(dto.getAvatar())
                .build();

        return userProfileRepository.save(profile);
    }

    public UserProfile updateProfile(Long userId, UserProfileRequestDto dto) {
        UserProfile profile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        // chỉ update trường nào có giá trị
        if (dto.getPhone() != null) profile.setPhone(dto.getPhone());
        if (dto.getName() != null) profile.setName(dto.getName());
        if (dto.getAvatar() != null) profile.setAvatar(dto.getAvatar());

        return userProfileRepository.save(profile);
    }

    public UserProfile getProfile(Long userId) {
        return userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }
}
