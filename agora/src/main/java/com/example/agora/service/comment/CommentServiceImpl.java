package com.example.agora.service.comment;

import com.example.agora.entity.comment.Comment;
import com.example.agora.entity.comment.CommentRepository;
import com.example.agora.entity.post.PostRepository;
import com.example.agora.exception.CommentNotFoundException;
import com.example.agora.exception.NoAuthorityException;
import com.example.agora.exception.PostNotFoundException;
import com.example.agora.payload.request.post.CmtIdRequest;
import com.example.agora.payload.request.post.CommentModifyRequest;
import com.example.agora.payload.response.MessageResponse;
import com.example.agora.security.jwt.auth.AuthDetails;
import com.example.agora.payload.request.post.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Date;
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    @Override
    public MessageResponse comment(CommentRequest request) {
        return postRepository.findById(Integer.parseInt(request.getPostId()))
                .map(post->{
                    AuthDetails AuthDetails = (AuthDetails)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
                    Comment comment = Comment.builder()
                            .contents(request.getComment())
                            .userId(AuthDetails.getUsername())
                            .post(post)
                            .createAt(new Date())
                            .build();
                    commentRepository.save(comment);
                    return new MessageResponse("댓글 작성 성공");
                }).orElseThrow(PostNotFoundException::new);
    }

    @Override
    public MessageResponse modifyComment(CommentModifyRequest request) {
        AuthDetails user = (AuthDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return commentRepository.findById(Integer.parseInt(request.getCmtId()))
                .map(comment->{
                    if(!user.getUsername().equals(comment.getUserId()))
                        throw new NoAuthorityException();
                    commentRepository.save(comment.Modify(request));
                    return new MessageResponse("댓글 수정 완료");
                }).orElseThrow(CommentNotFoundException::new);
    }

    @Override
    public MessageResponse commentLike(CmtIdRequest request) {
        return commentRepository.findById(Integer.parseInt(request.getCmtId()))
                .map(comment->{
                    commentRepository.save(comment.addLikes());
                    return new MessageResponse("성공!");
                }).orElseThrow(CommentNotFoundException::new);
    }
}
