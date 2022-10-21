package com.example.boardservice.module.member.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
public class RequestNicknameUpdateDto {

    @NotBlank
    private String nickname;
}
