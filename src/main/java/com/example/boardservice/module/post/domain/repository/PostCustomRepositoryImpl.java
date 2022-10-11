package com.example.boardservice.module.post.domain.repository;

import com.example.boardservice.module.post.web.dto.response.QResponsePostListDto;
import com.example.boardservice.module.post.web.dto.response.ResponsePostListDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.boardservice.module.post.domain.QPost.post;

@Repository
@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ResponsePostListDto> getMembersIncludingLastCreate(String author, String title, String content, Pageable pageable) {
        List<ResponsePostListDto> result = jpaQueryFactory.select(new QResponsePostListDto(
                        post.author,
                        post.title,
                        post.content,
                        post.viewCount
                ))
                .from(post)
                .where(isContainsAuthor(author),
                        isContainsTitle(title),
                        isContainsContent(content),
                        eqPostPublished(Boolean.TRUE))
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }

    @Override
    public Page<ResponsePostListDto> getPostViewCountSort(String author, String title, String content, Pageable pageable) {
        List<ResponsePostListDto> result = jpaQueryFactory.select(new QResponsePostListDto(
                        post.author,
                        post.title,
                        post.content,
                        post.viewCount
                ))
                .from(post)
                .where(isContainsAuthor(author),
                        isContainsTitle(title),
                        isContainsContent(content),
                        eqPostPublished(Boolean.TRUE))
                .orderBy(post.viewCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }

    private BooleanExpression isContainsTitle(String title) {
        if (Strings.isEmpty(title)) {
            return null;
        }
        return post.title.contains(title);
    }

    private  BooleanExpression isContainsContent(String content) {
        if (Strings.isEmpty(content)) {
            return null;
        }
        return post.content.contains(content);
    }

    private BooleanExpression isContainsAuthor(String author) {
        if (Strings.isEmpty(author)) {
            return null;
        }
        return post.author.contains(author);
    }

    private BooleanExpression eqPostPublished(Boolean published) {
        if (published == null) {
            return null;
        }
        return post.published.eq(published);
    }
}
