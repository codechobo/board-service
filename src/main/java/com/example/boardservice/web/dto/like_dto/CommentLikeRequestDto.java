package com.example.boardservice.web.dto.like_dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CommentLikeRequestDto {

    @JsonProperty("members_id")
    private Long memberId;

    @JsonProperty("comments_id")
    private Long commentId;
}
