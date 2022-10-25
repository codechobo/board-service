package com.example.boardservice.module.post.web.dto.response;

import com.example.boardservice.module.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponsePostSaveDto {

    private final LocalDateTime writeDateTime; // 작성 시간
    private final String author; // 글쓴이

    private final String title; // 제목

    private final String content; // 글 내용

    private final int viewCount; // 조회수

    private final long likes; // 좋아요 수


    @Builder
    public ResponsePostSaveDto(LocalDateTime writeDateTime, String author, String title, String content, int viewCount, long likes) {
        this.writeDateTime = writeDateTime;
        this.author = author;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.likes = likes;
    }

    public static ResponsePostSaveDto of(Post post) {
        return ResponsePostSaveDto.builder()
                .writeDateTime(post.getCreatedAt())
                .author(post.getAuthor())
                .title(post.getTitle())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .likes(post.getLikes())
                .build();
    }
}
