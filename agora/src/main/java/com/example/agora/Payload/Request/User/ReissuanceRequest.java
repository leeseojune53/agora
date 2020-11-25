package com.example.agora.Payload.Request.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReissuanceRequest {
    private String refreshToken;
}
