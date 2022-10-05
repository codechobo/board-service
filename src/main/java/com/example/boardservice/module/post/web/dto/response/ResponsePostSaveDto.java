package com.example.boardservice.module.post.web.dto.response;

import com.example.boardservice.module.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponsePostSaveDto {

    private final String author; // 글쓴이

    private final String title; // 제목

    private final String content; // 글 내용

    @Builder
    public ResponsePostSaveDto(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public static ResponsePostSaveDto of(Post post) {
        return ResponsePostSaveDto.builder()
                .author(post.getAuthor())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
