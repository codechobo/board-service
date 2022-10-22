package com.example.boardservice.module.hashtag.web.dto;

import com.example.boardservice.module.hashtag.domain.HashTag;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ResponseHashTagSaveDto {

    private final String hashTagName;

    public static ResponseHashTagSaveDto of(HashTag hashTag) {
        return ResponseHashTagSaveDto.builder().hashTagName(hashTag.getHashTagName()).build();
    }

}
