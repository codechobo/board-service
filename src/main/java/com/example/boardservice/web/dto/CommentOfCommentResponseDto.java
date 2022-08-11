package com.example.boardservice.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CommentOfCommentResponseDto {

    private final String title;
    private final String author;
    private final String content;
    private  List<CommentOfCommentResponseDto> comments = new ArrayList<>();

    @Builder
    public CommentOfCommentResponseDto(String title, String author, String content, List<CommentOfCommentResponseDto> comments) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.comments = comments;
    }
}
