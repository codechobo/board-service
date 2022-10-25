package com.example.boardservice.module.hashtag.web.dto.response;

import com.example.boardservice.module.hashtag.domain.HashTag;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ResponseHashTagListDto {

    private final Set<String> hashTagNames;

    @Builder
    public ResponseHashTagListDto(Set<HashTag> hashTagNames) {
        this.hashTagNames = hashTagNames.stream()
                .map(HashTag::getHashTagName)
                .collect(Collectors.toSet());
    }

}
