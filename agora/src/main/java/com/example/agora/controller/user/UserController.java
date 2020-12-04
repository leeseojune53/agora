package com.example.agora.controller.user;

import com.example.agora.payload.request.user.LoginRequest;
import com.example.agora.payload.request.user.ReissuanceRequest;
import com.example.agora.payload.request.user.UserRequest;
import com.example.agora.payload.response.user.TokenResponse;
import com.example.agora.payload.response.user.MypageResponse;
import com.example.agora.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/auth")
    public TokenResponse user_login(@RequestBody LoginRequest request){
        log.info("\nPOST /auth\nuserId : " + request.getUserId() + "\nuserPw : " + request.getUserPw());
        return userService.userLogin(request);
    }

    @PostMapping("/register")
    public String Register(@RequestBody UserRequest request){
        log.info("\nPOST /register\nuserId : " + request.getUserId() + "\nuserPw : " + request.getUserPw());
        return userService.register(request);
    }

    @GetMapping("/mypage")
    public MypageResponse myPage(){
        log.info("\nGET /mypage");
        return userService.myPage();
    }

    @PostMapping("/auth/reissuance")
    public TokenResponse updateToken(@RequestBody ReissuanceRequest request){
        log.info("\nPOST /auth/reissuance");
        return userService.updateToken(request);
    }

}
