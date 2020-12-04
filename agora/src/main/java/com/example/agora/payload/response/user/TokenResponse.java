package com.example.agora.payload.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {
    private final String AccessToken;
    private final String RefreshToken;
    private final Long RefreshToken_exp;



}