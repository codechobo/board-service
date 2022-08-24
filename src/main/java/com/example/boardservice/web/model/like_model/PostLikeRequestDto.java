package com.example.boardservice.web.model.like_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostLikeRequestDto {

    @JsonProperty("members_id")
    private Long memberId;

    @JsonProperty("posts_id")
    private Long postId;

}
