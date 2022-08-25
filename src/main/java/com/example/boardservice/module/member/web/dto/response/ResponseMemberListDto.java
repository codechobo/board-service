package com.example.boardservice.module.member.web.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ResponseMemberListDto {

    private String name;
    private String nickname;
    private String email;
    private LocalDateTime joinedAt;

    @Builder
    @QueryProjection
    public ResponseMemberListDto(String name, String nickname, String email, LocalDateTime joinedAt) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.joinedAt = joinedAt;
    }
}
