package com.example.agora.entity.post;

import com.example.agora.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByTitleContaining(String title);
    List<Post> findTop100ByOrderByModifyAtDescCreateAtDesc();
    int deleteByPostId(int postId);
    List<Post> findAllByUser(User user);
}
