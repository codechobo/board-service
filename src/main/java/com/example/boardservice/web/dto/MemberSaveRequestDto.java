package com.example.boardservice.web.dto;

import com.example.boardservice.domain.Member;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class MemberSaveRequestDto {

    @Size(min = 1, max = 50)
    @NotBlank
    @JsonProperty("name")
    private String name;

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
    public MemberSaveRequestDto(String name, String nickname, String email, String password) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public Member toEntity() {
        return Member.builder()
                .name(this.name)
                .nickname(this.nickname)
                .email(this.email)
                .password(this.password)
                .build();
    }

}
