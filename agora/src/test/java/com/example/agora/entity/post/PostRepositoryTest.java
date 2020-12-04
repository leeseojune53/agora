package com.example.agora.entity.post;

import com.example.agora.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.config.location=classpath:application-test.properties"})
public class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    private Post post;

    @BeforeEach
    public void setup(){
        this.post = postRepository.save(
                Post.builder()
                        .title("testTitle")
                        .contents("testContents")
                        .user(User.builder().userId("test").build())
                        .build()
        );
    }
}
