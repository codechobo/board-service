package com.example.boardservice.module.like.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CommentLikeRequestDto {

    @JsonProperty("members_id")
    @NotNull
    private Long memberId;

    @JsonProperty("comments_id")
    @NotNull
    private Long commentId;
}
