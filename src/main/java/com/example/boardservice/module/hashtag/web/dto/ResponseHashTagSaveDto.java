package com.example.boardservice.module.hashtag.web.dto;

import com.example.boardservice.module.hashtag.domain.HashTag;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@RequiredArgsConstructor
public class ResponseHashTagSaveDto {

    private final List<String> hashTagName;

    public static ResponseHashTagSaveDto of(List<HashTag> hashTag) {
        return ResponseHashTagSaveDto.builder()
                .hashTagName(hashTag.stream().map(HashTag::getHashTagName).collect(Collectors.toList()))
                .build();
    }

}
