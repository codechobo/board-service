package com.example.boardservice.module.comment.web.dto.response;

import com.example.boardservice.module.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentOfCommentResponseDto {

    private final LocalDateTime writeDateTime; // 작성시간
    private final String author; // 작성자
    private final String content; // 댓글 내용
    private final List<CommentOfCommentResponseDto> comments; // 대 댓글 리스트

    @Builder
    private CommentOfCommentResponseDto(Comment comment) {
        this.writeDateTime = comment.getCreatedAt();
        this.author = comment.getAuthor();
        this.content = comment.getContent();
        this.comments = comment.getChild().stream().map(CommentOfCommentResponseDto::of)
                .collect(Collectors.toList());
    }

    public static CommentOfCommentResponseDto of(Comment comment) {
        return CommentOfCommentResponseDto.builder()
                .comment(comment)
                .build();
    }
}