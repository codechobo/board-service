package com.example.boardservice.module.member.service;

import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.module.member.domain.Member;
import com.example.boardservice.module.member.domain.repository.MemberRepository;
import com.example.boardservice.module.member.web.dto.request.MemberSaveRequestDto;
import com.example.boardservice.module.member.web.dto.request.MemberUpdateRequestDto;
import com.example.boardservice.module.member.web.dto.response.ResponseMemberListDto;
import com.example.boardservice.module.member.web.dto.response.ResponseMembersPageDto;
import com.sun.jdi.request.DuplicateRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberService memberService;

    @Test
    @DisplayName("회원 정보 저장시에 중복된 정보로 저장하는 경우 DuplicateRequestException 예외 발생한다 -> " +
            "저장 메서드가 호출 되지 않아야 한다.")
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
        given(memberRepository.findById(anyLong()))
                .willThrow(new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));

        // when && then
        assertThatThrownBy(() -> memberService.findMemberById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(ErrorCode.NOT_FOUND_ENTITY.getMessage());
    }

    @Test
    @DisplayName("멤버 이름으로 검색하여 회원 리스트를 조회되며, 최근에 가입된 순서대로 정렬되어 반환된다. " +
            "이름을 넣지 않는 경우 모든 리스트가 조회되며 " +
            "이름을 넣은 경우 해당 이름인 회원 리스트가 반환된다.")
    void findMembers() {
        // given
        List<ResponseMemberListDto> content = new ArrayList<>();

        String name = "이기영";
        for (int i = 0; i < 10; i++) {
            String nickname = "까까머리";
            String email = "기영@naver.com";
            ResponseMemberListDto dto = ResponseMemberListDto.builder()
                    .name(name)
                    .nickname(nickname + i)
                    .email(email + i)
                    .joinedAt(LocalDateTime.now())
                    .build();
            content.add(dto);
        }

        Pageable pageable = Pageable.ofSize(10);
        PageImpl<ResponseMemberListDto> responseMemberListDtos = new PageImpl<>(content, pageable, content.size());
        given(memberRepository.getMembersIncludingLastJoin(anyString(), any(Pageable.class)))
                .willReturn(responseMemberListDtos);

        // when
        ResponseMembersPageDto result = memberService.getMemberListIncludingLastJoin(name, pageable);

        // then
        assertAll(
                () -> assertThat(result.getElements().containsAll(content)).isTrue(),
                () -> assertThat(result.getPageSize()).isEqualTo(pageable.getPageSize()),
                () -> assertThat(result.getCurrentPage()).isEqualTo(0),
                () -> assertThat(result.getTotalPage()).isEqualTo(1)
        );

        verify(memberRepository).getMembersIncludingLastJoin(name, pageable);
    }

    @Test
    @DisplayName("회원을 찾을 다음 해당 해원 정보를 업데이트 한다. 데이터 변경이 일어나야 한다.")
    void updateAfterFindMember() {
        // give
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

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));

        // when
        memberService.updateAfterFindMember(memberUpdateRequestDto);

        // then
        assertThat(member.getNickname()).isEqualTo(memberUpdateRequestDto.getNickname());
        assertThat(member.getEmail()).isEqualTo(memberUpdateRequestDto.getEmail());

        verify(memberRepository).findById(anyLong());
    }

    @Test
    @DisplayName("해당 엔티티를 찾지 못한 경우 EntityNotFoundException 예외가 발생하며 delete() 메서드가 실행되지 않아야 한다.")
    void removeMember() {
        // given
        given(memberRepository.findById(anyLong()))
                .willThrow(new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));

        // when && then
        assertThatThrownBy(() -> memberService.removeMember(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(ErrorCode.NOT_FOUND_ENTITY.getMessage());

        verify(memberRepository, times(0)).save(any(Member.class));
    }
}