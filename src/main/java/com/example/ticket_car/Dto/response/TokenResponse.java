package com.example.ticket_car.Dto.response;

import lombok.*;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse<T> {
    private String accessToken;
    private String refreshToken;
}
