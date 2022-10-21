package com.example.boardservice.module.post.web.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class RequestSearchPostDto {

    @NotEmpty
    private String author;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @Builder
    public RequestSearchPostDto(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }
}
