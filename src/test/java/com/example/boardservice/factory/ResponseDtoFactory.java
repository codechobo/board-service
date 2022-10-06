package com.example.boardservice.factory;

import com.example.boardservice.module.member.domain.Member;
import com.example.boardservice.module.member.web.dto.response.ResponseMemberSaveDto;

public class ResponseDtoFactory {
    public static ResponseMemberSaveDto createResponseSaveMemberDto(Member member) {
        return ResponseMemberSaveDto.builder()
                .member(member)
                .build();
    }
}
