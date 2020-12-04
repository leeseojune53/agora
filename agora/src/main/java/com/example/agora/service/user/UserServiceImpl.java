package com.example.agora.service.user;

import com.example.agora.entity.post.Post;
import com.example.agora.entity.user.User;
import com.example.agora.entity.user.UserRepository;
import com.example.agora.exception.AlreadyExistException;
import com.example.agora.exception.InvalidTokenException;
import com.example.agora.exception.NoAuthorityException;
import com.example.agora.exception.UserNotFoundException;
import com.example.agora.payload.request.user.LoginRequest;
import com.example.agora.payload.request.user.ReissuanceRequest;
import com.example.agora.payload.request.user.UserRequest;
import com.example.agora.payload.response.post.Search.SearchData;
import com.example.agora.payload.response.user.TokenResponse;
import com.example.agora.payload.response.user.MypageResponse;
import com.example.agora.redis.RedisUtilService;
import com.example.agora.security.AuthenticationFacade;
import com.example.agora.security.AuthorityType;
import com.example.agora.security.jwt.auth.AuthDetails;
import com.example.agora.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtilService redisUtilService;
    private final AuthenticationFacade authFacade;

    @Value("${jwt.exp.refresh}")
    private Long RefreshToken_exp;

    @Override
    public String register(UserRequest request) {
        try{
            userRepository.save(
                    User.builder()
                            .authorityType(AuthorityType.ROLE_USER)
                            .userId(request.getUserId())
                            .userPw(passwordEncoder.encode(request.getUserPw()))
                            .build()
            );
            return "Success";
        }catch (Exception e){
            throw new AlreadyExistException();
        }
    }

    @Override
    public TokenResponse userLogin(LoginRequest request) {
        return userRepository.findByUserId(request.getUserId())
                .filter(user-> passwordEncoder.matches(request.getUserPw(), user.getUserPw()))
                .map(User::getUserCode)
                .map(userCode->{
                    try{
                        authenticationManager.authenticate(request.getAuthToken(userCode));
                    }catch (Exception e){
                        throw new InvalidTokenException();
                    }
                    String accessToken = jwtTokenProvider.generateAccessToken(userCode);
                    String refreshToken = jwtTokenProvider.generateRefreshToken(userCode);
                    String key = "user:" + userCode;
//                    refreshTokenRepository.save(new RefreshToken(userCode, refreshToken));
                    redisUtilService.setDataExpire(key, refreshToken, RefreshToken_exp);
                    return new TokenResponse(accessToken, refreshToken, RefreshToken_exp);
                })
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public MypageResponse myPage() {
        List<SearchData> dataList = new ArrayList<>();
        List<Post> postList = authFacade.getPosts();
        for(Post post:postList){
            dataList.add(post.myPage());
        }

        return new MypageResponse(authFacade.getUserName(), dataList);
    }

    @Override
    public TokenResponse updateToken(ReissuanceRequest request) {
        try{
            if(jwtTokenProvider.isRefreshToken(request.getRefreshToken())){

                try{
                    String key = "user:"  + authFacade.getUserCode();
                    if(redisUtilService.getData(key).equals(request.getRefreshToken())){
                        String accessToken = jwtTokenProvider.generateAccessToken(authFacade.getUserCode());
                        String newRefreshToken = jwtTokenProvider.generateRefreshToken(authFacade.getUserCode());
                        redisUtilService.setDataExpire(key, newRefreshToken, RefreshToken_exp);
                        return new TokenResponse(accessToken, newRefreshToken, RefreshToken_exp);
                    }else throw new InvalidTokenException();
                }catch(Exception e){
                    throw new UserNotFoundException();
                }
            }else throw new InvalidTokenException();
        }catch (Exception e){
            throw new NoAuthorityException();
        }

    }


}
