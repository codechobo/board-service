package com.example.boardservice.module.member.service;

import com.example.DtoInstanceProvider;
import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.module.member.domain.Member;
import com.example.boardservice.module.member.domain.repository.MemberRepository;
import com.example.boardservice.module.member.web.dto.request.MemberSaveRequestDto;
import com.example.boardservice.module.member.web.dto.request.MemberUpdateRequestDto;
import com.example.boardservice.module.member.web.dto.response.MemberSaveResponseDto;
import com.example.boardservice.module.member.web.dto.response.ResponseMemberListDto;
import com.example.boardservice.module.member.web.dto.response.ResponseMembersPageDto;
import com.sun.jdi.request.DuplicateRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberService memberService;

    @Test
    @DisplayName("회원 정보 저장시에 중복된 정보로 저장하는 경우 DuplicateRequestException 예외 발생한다 -> 저장 메서드가 호출 되지 않아야 한다.")
    void saveMember_throwException() {
        // given
        MemberSaveRequestDto memberSaveRequestDto = MemberSaveRequestDto.builder()
                .name("이기영")
                .email("기영@naver.com")
                .nickname("까까머리")
                .password("test1234")
                .build();

        given(memberRepository.existsByNicknameAndEmailAndPassword(
                memberSaveRequestDto.getNickname(),
                memberSaveRequestDto.getEmail(),
                memberSaveRequestDto.getPassword()))
                .willReturn(Boolean.TRUE);

        // when && then
        assertThatThrownBy(() -> memberService.saveMember(memberSaveRequestDto))
                .isInstanceOf(DuplicateRequestException.class)
                .hasMessage(ErrorCode.REQUEST_DATA_DUPLICATED.getMessage());

        verify(memberRepository, times(0)).save(memberSaveRequestDto.toEntity());
    }

    @Test
    @DisplayName("멤버 조회시 memberId가 존재 하지 않을 때 EntityNotFoundException 예외가 발생한다.")
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
    @DisplayName("이름으로 멤버 정보 리스트로 가져오기 -> 페이징 처리 내림차순")
    void findMembers() {
        // given
        List<ResponseMemberListDto> content = new ArrayList<>();
        Member member = null;
        for (int i = 0; i < 10; i++) {
            member = DtoInstanceProvider.createMemberSaveRequestDto().toEntity();

            ResponseMemberListDto responseMemberListDto = ResponseMemberListDto.builder()
                    .name(member.getName())
                    .nickname(member.getNickname())
                    .email(member.getEmail())
                    .joinedAt(member.getCreatedAt())
                    .build();

            content.add(responseMemberListDto);
        }

        Page<ResponseMemberListDto> page =
                new PageImpl<>(content, Pageable.ofSize(5), content.size());

        given(memberRepository.getMembersIncludingLastJoin(anyString(), any(Pageable.class)))
                .willReturn(page);

        // when
        ResponseMembersPageDto result = memberService
                .getMemberListIncludingLastJoin(member.getName(), Pageable.ofSize(5));

        // then
        assertAll(
                () -> assertThat(result.getElements().containsAll(content)).isTrue(),
                () -> assertThat(result.getPageSize()).isEqualTo(5),
                () -> assertThat(result.getCurrentPage()).isEqualTo(0),
                () -> assertThat(result.getTotalPage()).isEqualTo(2)
        );

        verify(memberRepository).getMembersIncludingLastJoin(member.getName(), Pageable.ofSize(5));
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