package com.example.boardservice.service;

import com.example.DtoInstanceProvider;
import com.example.boardservice.domain.Member;
import com.example.boardservice.domain.repository.MemberRepository;
import com.example.boardservice.web.dto.member_dto.MemberSaveRequestDto;
import com.example.boardservice.web.dto.member_dto.MemberSaveResponseDto;
import com.example.boardservice.web.dto.member_dto.MemberUpdateRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
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
        // given
        MemberSaveRequestDto memberSaveRequestDto = DtoInstanceProvider.createMemberSaveRequestDto();
        given(memberRepository.save(any(Member.class)))
                .willReturn(memberSaveRequestDto.toEntity());

        // when
        MemberSaveResponseDto result = memberService
                .saveMember(memberSaveRequestDto);

        // then
        assertThat(result.getNickname()).isEqualTo(memberSaveRequestDto.getNickname());
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    @DisplayName("Member 정보 조회하다 -> id 값")
    void findMemberById() {
        // given
        MemberSaveRequestDto memberSaveRequestDto = DtoInstanceProvider.createMemberSaveRequestDto();
        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(memberSaveRequestDto.toEntity()));

        // when
        MemberSaveResponseDto result = memberService.findMemberById(1L);

        // then
        assertThat(result.getNickname()).isEqualTo(memberSaveRequestDto.getNickname());
        verify(memberRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Member 모든 정보 조회")
    void findMembers() {
        // given
        Member member = DtoInstanceProvider.createMemberSaveRequestDto().toEntity();
        List<Member> members = new ArrayList<>() {{
            add(member);
        }};

        given(memberRepository.findAll())
                .willReturn(members);

        // when
        List<MemberSaveResponseDto> result = memberService.findMembers();

        // then
        assertAll(
                () -> assertThat(result.iterator().next().getNickname()).isEqualTo(member.getNickname()),
                () -> assertThat(result.size()).isEqualTo(1),
                () -> assertThat(result.get(0).getNickname()).isEqualTo(member.getNickname())
        );

        verify(memberRepository).findAll();
    }

    @Test
    @DisplayName("Member 업데이트")
    void updateAfterFindMember() {
        MemberUpdateRequestDto memberUpdateRequestDto = MemberUpdateRequestDto.builder()
                .memberId(1L)
                .nickname("이기영")
                .email("기영@naver.com")
                .password("test1234")
                .build();

        Member member = Member.builder()
                .name("이기철")
                .nickname("기영이 형")
                .email("기철@naver.com")
                .password("test12345")
                .build();
        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(member));

        memberService.updateAfterFindMember(memberUpdateRequestDto);
        verify(memberRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Member 삭제한다.")
    void removeMember() {
        Member member = Member.builder()
                .name("이기철")
                .nickname("기영이 형")
                .email("기철@naver.com")
                .password("test12345")
                .build();
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        willDoNothing().given(memberRepository).delete(any(Member.class));

        memberService.removeMember(anyLong());

        verify(memberRepository).findById(anyLong());
        verify(memberRepository).delete(any(Member.class));

    }
}