package com.example.boardservice.web.dto;

import com.example.boardservice.domain.Like;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LikeSaveResponseDto {

    private final Long memberId;
    private final Long postId;

    @Builder
    public LikeSaveResponseDto(Like like) {
        this.memberId = like.getMembersId();
        this.postId = like.getPostsId();
    }
}
