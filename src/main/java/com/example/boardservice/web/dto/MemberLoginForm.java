package com.example.boardservice.web.dto;

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
