package com.example.boardservice.service;

import com.example.boardservice.MemberInstanceProvider;
import com.example.boardservice.domain.Member;
import com.example.boardservice.domain.repository.MemberRepository;
import com.example.boardservice.web.dto.MemberSaveRequestDto;
import com.example.boardservice.web.dto.MemberSaveResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberService memberService;

    @Test
    @DisplayName("Member 정보 저장한다.")
    void saveMember() {
        Member member = MemberInstanceProvider.createMemberSaveRequestDto().toEntity();
        given(memberRepository.save(any(Member.class)))
                .willReturn(member);

        MemberSaveResponseDto result = memberService
                .saveMember(MemberInstanceProvider.createMemberSaveRequestDto());

        assertThat(result.getNickname()).isEqualTo(member.getNickname());
        verify(memberRepository).save(any(Member.class));
    }
}