package com.example.boardservice.module.member.web.model;

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
