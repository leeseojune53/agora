package com.example.agora.payload.request.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentModifyRequest {
    private String cmtId;
    private String comment;
}
