package com.example.boardservice.module.comment.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentOfCommentRequestDto {

    @NotEmpty
    @Size(max = 30)
    private String author;

    @NotEmpty
    private String content;
}
