package com.example.boardservice.module.post.web.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponsePostListDto {

    private String author;
    private String title;
    private String content;

    @Builder
    @QueryProjection
    public ResponsePostListDto(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }
}
