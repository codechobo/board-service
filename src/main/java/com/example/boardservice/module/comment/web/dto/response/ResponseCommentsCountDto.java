package com.example.boardservice.module.comment.web.dto.response;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseCommentsCountDto {

    private final String postTitle;
    private final String author;
    private final Long commentCount;

    public static ResponseCommentsCountDto of(String postTitle, String author, Long commentCount) {
        return new ResponseCommentsCountDto(postTitle, author, commentCount);
    }
}
