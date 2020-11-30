package com.example.agora.Controller.Post;

import com.example.agora.Payload.Request.Post.*;
import com.example.agora.Payload.Response.MessageResponse;
import com.example.agora.Payload.Response.Post.View.PreviewResponse;
import com.example.agora.Payload.Response.Post.Search.SearchResponse;
import com.example.agora.Payload.Response.Post.View.ViewResponse;
import com.example.agora.Service.Comment.CommentService;
import com.example.agora.Service.Post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
@CrossOrigin(origins = "*")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    @PostMapping("/write")
    public MessageResponse write(@RequestBody WriteRequest request){
        log.info("\nPOST /post/write\nTitle : " + request.getTitle() + "\nContents : " + request.getContents());
        return postService.write(request);
    }

    @GetMapping("/preview/{postId}")
    public PreviewResponse preview(@PathVariable("postId") String postId){
        log.info("\nPOST /post/preview\nPostId : " + postId);
        return postService.preview(postId);
    }

    @GetMapping("/view/{postId}")
    public ViewResponse view(@PathVariable("postId") String postId){
        log.info("\nPOST /post/view\nPostId : " + postId);
        return postService.view(postId);
    }

    @GetMapping("/search/{title}")
    public SearchResponse search(@PathVariable("title") String title){
        log.info("\nPOST /post/search\nTitle : " + title);
        return postService.search(title);
    }

    @GetMapping("/list")
    public SearchResponse list(){
        log.info("\nGET /post/list");
        return postService.list();
    }

    @PatchMapping("/modify")
    public MessageResponse modify(@RequestBody ModifyRequest request){
        log.info("\nPATCH /post/modify\nPostId : " + request.getPostId() + "\nTitle : " + request.getTitle() + "\nContents : " + request.getContents());
        return postService.modify(request);
    }

    @PatchMapping("/comment/modify")
    public MessageResponse commentModify(@RequestBody CommentModifyRequest request){
        log.info("\nPATCH /post/comment/modify\nCmtId : " + request.getCmtId() + "\nComment : " + request.getComment());
        return commentService.modifyComment(request);
    }

    @DeleteMapping("/delete/{postId}")
    @Transactional
    public MessageResponse delete(@PathVariable("postId") String postId){
        log.info("\nPOST /post/delete\nPostId : " + postId);
        return postService.delete(postId);
    }

    @PostMapping("/comment")
    public MessageResponse comment(@RequestBody CommentRequest request){
        log.info("POST /post/comment\nPostId : " + request.getPostId() + "\nComment : " + request.getComment());
        return commentService.comment(request);
    }

    @PatchMapping("/like")
    public MessageResponse like(@RequestBody PostIdRequest request){
        log.info("PATCH /post/like\nPostId : " + request.getPostId());
        return postService.like(request);
    }

    @PatchMapping("/comment/like")
    public MessageResponse commentLike(@RequestBody CmtIdRequest request){
        log.info("POST /post/comment/like\nCmtId : " + request.getCmtId());
        return commentService.commentLike(request);
    }

}
