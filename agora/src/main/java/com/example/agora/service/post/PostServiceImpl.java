package com.example.agora.service.post;

import com.example.agora.entity.comment.Comment;
import com.example.agora.entity.post.Post;
import com.example.agora.entity.post.PostRepository;
import com.example.agora.entity.user.UserRepository;
import com.example.agora.exception.NoAuthorityException;
import com.example.agora.exception.PostNotFoundException;
import com.example.agora.exception.UserNotFoundException;
import com.example.agora.payload.request.post.ModifyRequest;
import com.example.agora.payload.request.post.PostIdRequest;
import com.example.agora.payload.request.post.WriteRequest;
import com.example.agora.payload.response.MessageResponse;
import com.example.agora.payload.response.post.Comment.CommentResponse;
import com.example.agora.payload.response.post.View.PreviewResponse;
import com.example.agora.payload.response.post.Search.SearchData;
import com.example.agora.payload.response.post.Search.SearchResponse;
import com.example.agora.payload.response.post.View.ViewResponse;
import com.example.agora.security.AuthorityType;
import com.example.agora.security.jwt.auth.AuthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public MessageResponse write(WriteRequest request) {
        try{
            AuthDetails authDetails = (AuthDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userRepository.findByUserId(authDetails.getUsername())
                    .map(user-> {

                        postRepository.save(
                                Post.builder()
                                        .title(request.getTitle())
                                        .contents(request.getContents())
                                        .user(user)
                                        .build()
                        );
                        System.out.println(user.getUserId());
                        return new MessageResponse("글 작성 성공");
                    });
            return new MessageResponse("글 작성 성공");
        }catch (Exception e){
            throw new UserNotFoundException();
        }
    }

    @Override
    public PreviewResponse preview(String postId) {
        return postRepository.findById(Integer.parseInt(postId))
                .map(post-> new PreviewResponse(Integer.toString(post.getPostId()), post.getTitle(), post.getUser().getUserId(), post.getCreateAt(), post.getModifyAt())).orElseThrow(PostNotFoundException::new);
    }

    @Override
    public ViewResponse view(String postId) {
        return postRepository.findById(Integer.parseInt(postId))
                .map(post-> {
                    postRepository.save(
                            post.addViews()
                    );
                    List<CommentResponse> responseList = new ArrayList<>();
                    for (Comment comment:post.getComments()) {
                        responseList.add(new CommentResponse(comment.getCmtId(), comment.getUserId(), comment.getContents(), comment.getCreateAt(), comment.getModifyAt(), comment.getLikes()));
                    }
                    return new ViewResponse(Integer.toString(post.getPostId()), post.getTitle(), post.getContents(), post.getUser().getUserId(), post.getCreateAt(), post.getModifyAt(), post.getView(), post.getLikes(), responseList);
                }).orElseThrow(PostNotFoundException::new);
    }

    @Override
    public SearchResponse search(String title) {
        List<Post> postList = postRepository.findAllByTitleContaining(title);
        return new SearchResponse(convertList(postList));
    }

    @Override
    public SearchResponse list() {
        List<Post> postList = postRepository.findTop100ByOrderByModifyAtDescCreateAtDesc();
        return new SearchResponse(convertList(postList));
    }

    @Override
    public MessageResponse modify(ModifyRequest request) {
        AuthDetails authDetails = (AuthDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postRepository.save(
                postRepository.findById(Integer.parseInt(request.getPostId()))
                        .map(post->{
                            if(!authDetails.getUsername().equals(post.getUser().getUserId())){
                                throw new NoAuthorityException();
                            }
                            return post.setModifyAt(request);
                        }).orElseThrow(PostNotFoundException::new)
        );
        return new MessageResponse("글 수정 완료");
    }


    @Override
    public MessageResponse delete(String postId) {
        AuthDetails authDetails = (AuthDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return postRepository.findById(Integer.parseInt(postId))
                .map(post->{

                    if(authDetails.getUsername().equals(post.getUser().getUserId()) || authDetails.getAuthorities().equals(AuthorityType.ROLE_ADMIN)){
                        postRepository.deleteByPostId(post.getPostId());
                    }else{
                        throw new NoAuthorityException();
                    }

                    return new MessageResponse("글 삭제 완료");
                }).orElseThrow(PostNotFoundException::new);
    }

    @Override
    public MessageResponse like(PostIdRequest request) {
        return postRepository.findById(Integer.parseInt(request.getPostId()))
                .map(post->{
                    postRepository.save(post.addLike());
                    return new MessageResponse("성공!");
                }).orElseThrow(PostNotFoundException::new);
    }

    public List<SearchData> convertList(List<Post> postList){
        List<SearchData> response = new ArrayList<>();
        for (Post post:postList) {
            response.add(new SearchData(Integer.toString(post.getPostId()), post.getTitle(), post.getUser().getUserId(), post.getCreateAt(), post.getModifyAt()));
        }
        return response;
    }


}
