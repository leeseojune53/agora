package com.example.agora;

import com.example.agora.entity.user.User;
import com.example.agora.entity.user.UserRepository;
import com.example.agora.payload.request.user.UserRequest;
import com.example.agora.security.AuthorityType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"spring.config.location=classpath:application-test.properties"})
@RunWith(SpringRunner.class)
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
