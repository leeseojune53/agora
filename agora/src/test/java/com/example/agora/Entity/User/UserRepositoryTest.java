package com.example.agora.Entity.User;

import com.example.agora.Security.AuthorityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.config.location=classpath:application-test.properties"})
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup(){
        this.user = userRepository.save(
                User.builder()
                .userId("testname")
                .userPw("1234")
                .authorityType(AuthorityType.ROLE_USER)
                .build()
        );
    }

    @Test
    public void userRepositoryTest(){
        assertEquals(user.getUserPw(), userRepository.findByUserId(user.getUserId()).get().getUserPw());
    }
}
