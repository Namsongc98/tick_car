package com.example.ticket_car.Dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponseDto {
    private Long userId;
    private String username;
    private String email;   // từ entity User
    private String phone;
    private String name;
    private String avatar;
}
