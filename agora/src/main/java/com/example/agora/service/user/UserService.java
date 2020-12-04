package com.example.agora.service.user;

import com.example.agora.payload.request.user.LoginRequest;
import com.example.agora.payload.request.user.ReissuanceRequest;
import com.example.agora.payload.request.user.UserRequest;
import com.example.agora.payload.response.user.TokenResponse;
import com.example.agora.payload.response.user.MypageResponse;

public interface UserService {
    public String register(UserRequest request);
    public TokenResponse userLogin(LoginRequest request);
    public MypageResponse myPage();
    public TokenResponse updateToken(ReissuanceRequest request);
}
