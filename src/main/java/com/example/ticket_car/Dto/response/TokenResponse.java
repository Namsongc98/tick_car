package com.example.ticket_car.Dto.response;

import lombok.*;
@Setter
@Getter
public class TokenResponse<T> {
    private String accessToken;
    private String refreshToken;
    private T user;
    public TokenResponse(String accessToken, String refreshToken,T user ) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }
}
