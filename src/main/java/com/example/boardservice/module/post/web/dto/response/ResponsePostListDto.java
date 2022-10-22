package com.example.boardservice.module.post.web.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ResponsePostListDto {

    private LocalDateTime writeDateTime;
    private String author;
    private String title;
    private String content;
    private int viewCount;

    @Builder
    @QueryProjection
    public ResponsePostListDto(LocalDateTime writeDateTime, String author, String title, String content, int viewCount) {
        this.writeDateTime = writeDateTime;
        this.author = author;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
    }
}
