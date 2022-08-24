package com.example.boardservice.web.model.comment_model;

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
