package com.example.boardservice.module.like.web.dto.request;

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
public class PostLikeRequestDto {

    @JsonProperty("members_id")
    @NotNull
    private Long memberId;

    @JsonProperty("posts_id")
    @NotNull
    private Long postId;

}
