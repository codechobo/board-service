package com.example.boardservice.module.comment.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentSaveRequestDto {

    @NotEmpty
    private String author;

    @NotEmpty
    private String content;

}
