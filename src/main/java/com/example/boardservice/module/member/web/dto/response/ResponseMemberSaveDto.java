package com.example.boardservice.module.member.web.dto.response;

import com.example.boardservice.module.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseMemberSaveDto {

    private final Long id;
    private final String nickname;

    @Builder
    public ResponseMemberSaveDto(Member member) {
        this.id = member.getId();
        this.nickname = member.getNickname();
    }
}
