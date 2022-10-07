package com.example.boardservice.module.comment.domain.repository;

import com.example.boardservice.module.post.domain.Post;

public interface CommentCustomRepository {

    long getPostCommentCount(Post post);
}
