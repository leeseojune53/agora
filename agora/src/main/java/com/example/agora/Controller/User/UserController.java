package com.example.agora.Controller.User;

import com.example.agora.Payload.Request.User.LoginRequest;
import com.example.agora.Payload.Request.User.ReissuanceRequest;
import com.example.agora.Payload.Request.User.UserRequest;
import com.example.agora.Payload.Response.User.TokenResponse;
import com.example.agora.Payload.Response.User.MypageResponse;
import com.example.agora.Service.User.UserService;
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
