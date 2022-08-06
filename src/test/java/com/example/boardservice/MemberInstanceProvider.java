package com.example.boardservice;

import com.example.boardservice.web.dto.MemberSaveRequestDto;

public class MemberInstanceProvider {

    public static MemberSaveRequestDto createMemberSaveRequestDto() {
        return MemberSaveRequestDto.builder()
                .name("이기영")
                .nickname("까까머리")
                .email("기영@naver.com")
                .password("test1234")
                .build();
    }
}
