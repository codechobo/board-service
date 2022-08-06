package com.example.boardservice.web.dto;

import com.example.boardservice.domain.Member;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class MemberUpdateRequestDto {

    @NotNull
    @JsonProperty("member_id")
    private Long memberId;

    @Size(min = 1, max = 50)
    @NotBlank
    @JsonProperty("nickname")
    private String nickname;

    @Email
    @NotBlank
    @JsonProperty("email")
    private String email;

    @Size(min = 1, max = 50)
    @NotBlank
    @JsonProperty("password")
    private String password;

    @Builder
    public MemberUpdateRequestDto(Long memberId, String nickname, String email, String password) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }
}
