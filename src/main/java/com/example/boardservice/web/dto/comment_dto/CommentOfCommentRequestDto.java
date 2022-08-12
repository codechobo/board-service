package com.example.boardservice.web.dto.comment_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentOfCommentRequestDto {

    private String author;
    private String content;
}