package com.example.agora.payload.response.post.View;

import com.example.agora.payload.response.post.Comment.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
public class ViewResponse {
    private final String postId;
    private final String title;
    private final String contents;
    private final String userId;
    private final Date createAt;
    private final Date modifyAt;
    private final int view;
    private final int likes;
    private final List<CommentResponse> Comments;
}
