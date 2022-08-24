package com.example.boardservice.module.like.web.model.like_model;

import com.example.boardservice.module.like.domain.Like;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostsLikeResponseDto {

    private final Long memberId;
    private final Long postId;

    @Builder
    public PostsLikeResponseDto(Like like) {
        this.memberId = like.getMembersId();
        this.postId = like.getPostsId();
    }
}
