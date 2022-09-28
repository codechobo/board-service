package com.example.boardservice.factory;

import com.example.boardservice.module.member.web.dto.request.RequestMemberSaveDto;

public class RequestDtoFactory {
    public static RequestMemberSaveDto createMemberSaveRequestDto() {
        return RequestMemberSaveDto.builder()
                .name("이기영")
                .nickname("까까머리")
                .email("까까머리@naver.com")
                .password("test1234")
                .build();
    }
}
