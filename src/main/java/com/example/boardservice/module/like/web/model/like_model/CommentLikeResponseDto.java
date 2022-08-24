package com.example.boardservice.module.like.web.model.like_model;

import com.example.boardservice.module.like.domain.Like;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentLikeResponseDto {

    private final Long memberId;
    private final Long commentId;

    @Builder
    public CommentLikeResponseDto(Like like) {
        this.memberId = like.getMembersId();
        this.commentId = like.getCommentsId();
    }
}
