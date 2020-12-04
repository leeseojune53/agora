package com.example.agora.payload.response.post.Search;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class SearchData {
    private final String postId;
    private final String title;
    private final String userId;
    private final Date createAt;
    private final Date ModifyAt;
}
