package com.example.agora.service.comment;

import com.example.agora.payload.request.post.CmtIdRequest;
import com.example.agora.payload.request.post.CommentModifyRequest;
import com.example.agora.payload.request.post.CommentRequest;
import com.example.agora.payload.response.MessageResponse;

public interface CommentService {
    public MessageResponse comment(CommentRequest request);
    public MessageResponse modifyComment(CommentModifyRequest request);
    public MessageResponse commentLike(CmtIdRequest request);
}
