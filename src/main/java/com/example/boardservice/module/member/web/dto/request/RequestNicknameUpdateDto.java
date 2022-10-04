package com.example.boardservice.module.member.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RequestNicknameUpdateDto {

    private String nickname;
}
