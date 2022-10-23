package com.example.boardservice.module.hashtag.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseHashTagListDto {

    private final List<String> hashTagName;

    @Builder
    public ResponseHashTagListDto(List<String> hashTagName) {
        this.hashTagName = hashTagName;
    }
}
