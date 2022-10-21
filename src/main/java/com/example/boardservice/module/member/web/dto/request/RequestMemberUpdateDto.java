package com.example.boardservice.module.member.web.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class RequestMemberUpdateDto {

    @Size(min = 1, max = 50)
    @NotBlank
    private String nickname;

    @Email
    @NotBlank
    private String email;

    @Size(min = 1, max = 50)
    @NotBlank
    private String password;

    @Builder
    public RequestMemberUpdateDto(Long memberId, String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }
}
