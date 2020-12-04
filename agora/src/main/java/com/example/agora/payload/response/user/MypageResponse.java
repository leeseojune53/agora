package com.example.agora.payload.response.user;

import com.example.agora.payload.response.post.Search.SearchData;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MypageResponse {
    private final String userId;
    private final List<SearchData> postList;
}
