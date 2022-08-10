package com.example.boardservice.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@Builder
@RequiredArgsConstructor
public class MemberAuthResponseDto {

    private final Long id;
    private final String nickName;

}
