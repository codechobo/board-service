package com.example.boardservice.web.dto;

import com.example.boardservice.domain.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentOfCommentResponseDto {

    private final String title;
    private final String author;
    private final String content;
    private Comment comment;

    @Builder
    public CommentOfCommentResponseDto(String title, String author, String content, Comment comment) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.comment = comment;
    }
}
