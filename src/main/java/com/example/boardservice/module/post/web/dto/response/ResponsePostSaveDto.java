package com.example.boardservice.module.post.web.dto.response;

import com.example.boardservice.module.category.domain.Category;
import com.example.boardservice.module.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponsePostSaveDto {

    private final String author; // 글쓴이

    private final String title; // 제목

    private final String content; // 글 내용

    private final int viewCount; // 조회수

    @Builder
    public ResponsePostSaveDto(String author, String title, String content, int viewCount) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
    }

    public static ResponsePostSaveDto of(Post post) {
        return ResponsePostSaveDto.builder()
                .author(post.getAuthor())
                .title(post.getTitle())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .build();
    }
}
