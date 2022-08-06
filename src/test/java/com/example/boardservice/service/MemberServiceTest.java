package com.example.boardservice.service;

import com.example.DtoInstanceProvider;
import com.example.boardservice.domain.Member;
import com.example.boardservice.domain.repository.MemberRepository;
import com.example.boardservice.web.dto.MemberSaveRequestDto;
import com.example.boardservice.web.dto.MemberSaveResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
        MemberSaveRequestDto memberSaveRequestDto = DtoInstanceProvider.createMemberSaveRequestDto();
        given(memberRepository.save(any(Member.class)))
                .willReturn(memberSaveRequestDto.toEntity());

        MemberSaveResponseDto result = memberService
                .saveMember(memberSaveRequestDto);

        assertThat(result.getNickname()).isEqualTo(memberSaveRequestDto.getNickname());
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    @DisplayName("Member 정보 조회하다 -> id 값")
    void findMemberById() {
        MemberSaveRequestDto memberSaveRequestDto = DtoInstanceProvider.createMemberSaveRequestDto();
        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(memberSaveRequestDto.toEntity()));

        MemberSaveResponseDto result = memberService.findMemberById(1L);

        assertThat(result.getNickname()).isEqualTo(memberSaveRequestDto.getNickname());
        verify(memberRepository).findById(anyLong());
    }
}