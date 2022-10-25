package com.example.boardservice.module.hashtag.web.dto;

import com.example.boardservice.module.hashtag.domain.HashTag;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ResponseHashTagListDto {

    private final List<String> hashTagNames;

    @Builder
    public ResponseHashTagListDto(List<HashTag> hashTagNames) {
        this.hashTagNames = hashTagNames.stream()
                .map(HashTag::getHashTagName)
                .collect(Collectors.toList());
    }

}
