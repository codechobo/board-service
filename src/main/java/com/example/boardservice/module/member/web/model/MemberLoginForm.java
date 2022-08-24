package com.example.boardservice.module.member.web.model;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginForm {

    private String nickname;
    private String password;

}
