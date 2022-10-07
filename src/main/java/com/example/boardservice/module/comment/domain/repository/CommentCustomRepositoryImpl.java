package com.example.boardservice.module.comment.domain.repository;

import com.example.boardservice.module.post.domain.Post;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;

import static com.example.boardservice.module.comment.domain.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public long getPostCommentCount(Post post) {
        return jpaQueryFactory.
                selectFrom(comment)
                .where(eqCommentPost(post))
                .stream().count();
    }

    private BooleanExpression eqCommentPost(Post post) {
        Post checkedPost = Objects.requireNonNull(post);
        return comment.post.eq(checkedPost);
    }
}
