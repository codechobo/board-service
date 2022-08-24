package com.example.boardservice.web.model.like_model;

import com.example.boardservice.domain.Like;
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
