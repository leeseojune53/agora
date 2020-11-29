package com.example.agora;

import com.example.agora.Entity.User.User;
import com.example.agora.Entity.User.UserRepository;
import com.example.agora.Payload.Request.User.UserRequest;
import com.example.agora.Security.AuthorityType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(value = {"test"})
class AgoraApplicationTests {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        userRepository.save(
                User.builder()
                .userId("testname")
                .userPw(passwordEncoder.encode("1234"))
                .authorityType(AuthorityType.ROLE_USER)
                .build()
        );
    }
    

    @Test
    void test() throws Exception {
        UserRequest userRequest = new UserRequest("testname", "1234");
        mvc.perform(post("/auth").content(new ObjectMapper().writeValueAsString(userRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andDo(print());
    }

}
