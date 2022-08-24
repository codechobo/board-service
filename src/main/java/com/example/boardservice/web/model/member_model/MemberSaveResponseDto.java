package com.example.boardservice.web.model.member_model;

import com.example.boardservice.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberSaveResponseDto {

    private final Long id;
    private final String nickname;

    @Builder
    public MemberSaveResponseDto(Member member) {
        this.id = member.getId();
        this.nickname = member.getNickname();
    }
}
