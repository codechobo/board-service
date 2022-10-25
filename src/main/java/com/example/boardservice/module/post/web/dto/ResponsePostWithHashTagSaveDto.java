package com.example.boardservice.module.post.web.dto;

import com.example.boardservice.module.hashtag.web.dto.response.ResponseHashTagSaveDto;
import com.example.boardservice.module.post.web.dto.response.ResponsePostSaveDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponsePostWithHashTagSaveDto {

    private final ResponseHashTagSaveDto responseHashTagSaveDto;
    private final ResponsePostSaveDto responsePostSaveDto;

    @Builder
    public ResponsePostWithHashTagSaveDto(ResponseHashTagSaveDto responseHashTagSaveDto, ResponsePostSaveDto responsePostSaveDto) {
        this.responseHashTagSaveDto = responseHashTagSaveDto;
        this.responsePostSaveDto = responsePostSaveDto;
    }

    public static ResponsePostWithHashTagSaveDto of(ResponseHashTagSaveDto responseHashTagSaveDto, ResponsePostSaveDto responsePostSaveDto) {
        return ResponsePostWithHashTagSaveDto.builder()
                .responseHashTagSaveDto(responseHashTagSaveDto)
                .responsePostSaveDto(responsePostSaveDto)
                .build();
    }
}
