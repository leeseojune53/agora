package com.example.agora.Controller.Post;

import com.example.agora.Payload.Request.Post.*;
import com.example.agora.Payload.Response.MessageResponse;
import com.example.agora.Payload.Response.Post.View.PreviewResponse;
import com.example.agora.Payload.Response.Post.Search.SearchResponse;
import com.example.agora.Payload.Response.Post.View.ViewResponse;
import com.example.agora.Service.Comment.CommentService;
import com.example.agora.Service.Post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@CrossOrigin(origins = "*")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    @PostMapping("/write")
    public MessageResponse write(@RequestBody WriteRequest request){
        return postService.write(request);
    }

    @PostMapping("/preview")
    public PreviewResponse preview(@RequestBody PostIdRequest request){
        System.out.println(request.getPostId());
        return postService.preview(request);
    }

    @PostMapping("/view")
    public ViewResponse view(@RequestBody PostIdRequest request){
        return postService.view(request);
    }

    @PostMapping("/search")
    public SearchResponse search(@RequestBody SearchRequest request){
        return postService.search(request);
    }

    @GetMapping("/list")
    public SearchResponse list(){
        return postService.list();
    }

    @PatchMapping("/modify")
    public MessageResponse modify(@RequestBody ModifyRequest request){
        return postService.modify(request);
    }

    @PatchMapping("/comment/modify")
    public MessageResponse commentModify(@RequestBody CommentModifyRequest request){
        return commentService.modifyComment(request);
    }

    @DeleteMapping("/delete")
    @Transactional
    public MessageResponse delete(@RequestBody PostIdRequest request){
        return postService.delete(request);
    }

    @PostMapping("/comment")
    public MessageResponse comment(@RequestBody CommentRequest request){
        return commentService.comment(request);
    }

    @PatchMapping("/like")
    public MessageResponse like(@RequestBody PostIdRequest request){
        return postService.like(request);
    }

    @PatchMapping("/comment/like")
    public MessageResponse commentLike(@RequestBody CmtIdRequest request){
        return commentService.commentLike(request);
    }

}