package com.example.agora.service.post;

import com.example.agora.payload.request.post.ModifyRequest;
import com.example.agora.payload.request.post.PostIdRequest;
import com.example.agora.payload.request.post.WriteRequest;
import com.example.agora.payload.response.MessageResponse;
import com.example.agora.payload.response.post.View.PreviewResponse;
import com.example.agora.payload.response.post.Search.SearchResponse;
import com.example.agora.payload.response.post.View.ViewResponse;

public interface PostService {
    public MessageResponse write(WriteRequest request);
    public PreviewResponse preview(String request);
    public ViewResponse view(String request);
    public SearchResponse search(String request);
    public SearchResponse list();
    public MessageResponse modify(ModifyRequest request);
    public MessageResponse delete(String request);
    public MessageResponse like(PostIdRequest request);
}
