package com.example.boardservice.module.like.web.model.like_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CommentLikeRequestDto {

    @JsonProperty("members_id")
    private Long memberId;

    @JsonProperty("comments_id")
    private Long commentId;
}
