package com.example.boardservice.web.model.comment_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentSaveRequestDto {

    @NotNull
    @JsonProperty("author")
    private String author;

    @NotNull
    @JsonProperty("content")
    private String content;

}
