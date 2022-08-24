package com.example.boardservice.web.model.member_model;

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
