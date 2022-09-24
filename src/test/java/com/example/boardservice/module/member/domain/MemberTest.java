package com.example.boardservice.module.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MemberTest {

    // given
    private String name = "이기영";
    private String nickname = "까까머리";
    private String email = "까까머리@naver.com";
    private String password = "test1234";

    @Test
    @DisplayName("Member 객체 생성 테스트")
    void createMember() {
        // when
        Member member = createMember(name, nickname, email, password);

        // then
        assertEquals(member.getName(), name);
        assertEquals(member.getNickname(), nickname);
        assertEquals(member.getEmail(), email);
        assertEquals(member.getPassword(), password);
    }

    @Test
    @DisplayName("Member 속성 업데이트 테스트")
    void updateMember() {
        String updateNickname = "돌머리";
        String updatePassword = "test12345";

        Member member = createMember(name, nickname, email, password);

        assertEquals(member.getNickname(), nickname);
        assertEquals(member.getPassword(), password);

        member.updateMember(updateNickname, updatePassword);

        assertEquals(member.getNickname(), updateNickname);
        assertEquals(member.getPassword(), updatePassword);
    }

    private Member createMember(String name, String nickname, String email, String password) {
        return Member.builder()
                .name(name)
                .nickname(nickname)
                .email(email)
                .password(password) // 암호화 생략
                .build();
    }
}