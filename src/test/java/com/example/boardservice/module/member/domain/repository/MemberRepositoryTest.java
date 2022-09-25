package com.example.boardservice.module.member.domain.repository;

import com.example.boardservice.module.member.domain.Member;
import com.example.boardservice.module.member.web.dto.request.RequestMemberSaveDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("닉네임과 비밀번호 이메일 중복된 데이터가 없으면 false 리턴")
    void existsByNicknameAndPasswordAndEmail_false() {
        boolean result = memberRepository.existsByNicknameOrPasswordOrEmail("까까머리", "test1234", "기영@naver.com");
        assertFalse(result);
    }

    @Test
    @DisplayName("닉네임과 비밀번호 이메일 중복된 데이터가 있으면 true 리턴")
    void existsByNicknameAndPasswordAndEmail_true() {
        RequestMemberSaveDto dto = RequestMemberSaveDto.builder()
                .name("이기영")
                .nickname("까까머리")
                .password("test1234")
                .email("test@naver.com")
                .build();
        Member member = Member.createMember(dto, passwordEncoder);
        memberRepository.save(member);

        boolean result = memberRepository.existsByNicknameOrPasswordOrEmail("까까머리", "test1234", "기영@naver.com");
        assertTrue(result);
    }

}