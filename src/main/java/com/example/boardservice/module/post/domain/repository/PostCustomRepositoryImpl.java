package com.example.boardservice.module.post.domain.repository;

import com.example.boardservice.module.post.web.dto.QResponsePostListDto;
import com.example.boardservice.module.post.web.dto.ResponsePostListDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
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
                        post.content
                ))
                .from(post)
                .where(post.author.contains(author),
                        post.title.contains(title),
                        post.content.contains(content))
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }
}
