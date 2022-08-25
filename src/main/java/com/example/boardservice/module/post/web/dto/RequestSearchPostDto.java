package com.example.boardservice.module.post.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class RequestSearchPostDto {

    @NotNull
    private String author;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @Builder
    public RequestSearchPostDto(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }
}
