package com.example.agora.payload.response.post.Search;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SearchResponse {
    private final List<SearchData> searchData;
}
