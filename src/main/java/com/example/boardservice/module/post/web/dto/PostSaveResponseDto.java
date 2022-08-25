package com.example.boardservice.module.post.web.dto;

import com.example.boardservice.module.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostSaveResponseDto {

    private final String author; // 글쓴이

    private final String title; // 제목

    private final String content; // 글 내용

    @Builder
    public PostSaveResponseDto(Post post) {
        this.author = post.getAuthor();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}