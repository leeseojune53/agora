package com.example.agora.Entity.RefreshToken;

import com.example.agora.Redis.RedisUtilService;
import com.example.agora.Redis.RedisUtilServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.config.location=classpath:application-test.properties"})
public class RefreshTokenRepositoryTest {
    @Autowired
    private RedisUtilService redisUtilService;

    private RefreshToken refreshToken;

    @BeforeEach
    private void setup(){
        this.refreshToken = new RefreshToken(1, redisUtilService.setDataExpire("testkey", "testrefreshtoken", 500L));
    }

    @Test
    public void findByRefreshTokenTest(){
        assertEquals(refreshToken.getRefreshToken(), redisUtilService.getData("testkey"));
    }
}
