package com.example.boardservice.web.dto;

import com.example.boardservice.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentOfCommentResponseDto {

    private final String author;
    private final String content;
    private final List<CommentOfCommentResponseDto> comments;

    @Builder
    public CommentOfCommentResponseDto(Comment comment) {
        this.author = comment.getAuthor();
        this.content = comment.getContent();
        this.comments = comment.getChild().stream()
                .map(CommentOfCommentResponseDto::new)
                .collect(Collectors.toList());;
    }
}