package com.example.boardservice.web.dto;

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
public class LikeSaveRequestDto {

    @NotNull
    @JsonProperty("members_id")
    private Long memberId;

    @NotNull
    @JsonProperty("posts_id")
    private Long postId;
}
