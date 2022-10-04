package com.example.boardservice.module.member.service;

import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.factory.RequestDtoFactory;
import com.example.boardservice.module.member.domain.Member;
import com.example.boardservice.module.member.domain.repository.MemberRepository;
import com.example.boardservice.module.member.exception.NotMatchPasswordException;
import com.example.boardservice.module.member.web.dto.request.RequestNicknameUpdateDto;
import com.example.boardservice.module.member.web.dto.request.RequestMemberSaveDto;
import com.example.boardservice.module.member.web.dto.request.RequestPasswordUpdateDto;
import com.example.boardservice.module.member.web.dto.response.ResponseMembersPageDto;
import com.sun.jdi.request.DuplicateRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 정보 저장 테스트 성공")
    void saveMember_success() {
        // given
        String nickname = "까까머리";
        String email = "기영@naver.com";
        String password = "test1234";

        // when
        Member member = saveMember();

        // then
        assertNotNull(member);
        assertEquals(member.getNickname(), nickname);
        assertEquals(member.getEmail(), email);
        assertTrue(passwordEncoder.matches(password, member.getPassword()));
    }

    @Test
    @DisplayName("회원 정보 저장 테스트 실패 -> 중복된 정보 포함하여 DuplicateRequestException 예외 던짐")
    void saveMember_fail() {
        // given
        RequestMemberSaveDto dto = RequestDtoFactory.createMemberSaveRequestDto();
        saveMember();

        // when && then
        assertThatThrownBy(() -> memberService.saveMember(dto))
                .isInstanceOf(DuplicateRequestException.class)
                .hasMessage(ErrorCode.REQUEST_DATA_DUPLICATED.getMessage());
    }

    @Test
    @DisplayName("회원 이름으로 검색하여 가입 시간 기준으로 최신으로 정렬 한 다음 회원 페이지 리스트 가져오기 테스트")
    void getMemberListIncludingLastJoin() {
        // given
        saveMember();

        // when
        ResponseMembersPageDto responseMembersPageDto = memberService.getMemberListIncludingLastJoin("이기영", Pageable.ofSize(5));

        // then
        assertThat(responseMembersPageDto.getCurrentPage()).isEqualTo(0);
        assertThat(responseMembersPageDto.getTotalPage()).isEqualTo(1);
        assertThat(responseMembersPageDto.getElementsSize()).isEqualTo(1);
        assertThat(responseMembersPageDto.getPageSize()).isEqualTo(5);
        assertThat(responseMembersPageDto.getElements().get(0).getName()).isEqualTo("이기영");
    }

    @Test
    @DisplayName("회원 비밀번호 업데이트 테스트 성공")
    void updateAfterFindMember_success() {
        // given
        Member member = saveMember();

        RequestPasswordUpdateDto passwordUpdateDto = new RequestPasswordUpdateDto();
        passwordUpdateDto.setNewPassword("test12345");
        passwordUpdateDto.setNewPasswordConfirm("test12345");

        // when
        memberService.updatePasswordAfterFindMember(member.getId(), passwordUpdateDto);

        // then
        Member findMember = memberRepository.findById(member.getId()).get();
        assertTrue(passwordEncoder.matches(passwordUpdateDto.getNewPassword(), findMember.getPassword()));
    }

    @Test
    @DisplayName("회원 비밀번호가 일치하지 않는 경우 업데이트 실패")
    void updateAfterFindMember_fail() {
        // given
        Member member = saveMember();

        RequestPasswordUpdateDto passwordUpdateDto = new RequestPasswordUpdateDto();
        passwordUpdateDto.setNewPassword("test123");
        passwordUpdateDto.setNewPasswordConfirm("test12345");

        // when && then
        assertThatThrownBy(() -> memberService.updatePasswordAfterFindMember(member.getId(), passwordUpdateDto))
                .isInstanceOf(NotMatchPasswordException.class)
                .overridingErrorMessage(ErrorCode.NOT_MATCH_PASSWORD.getMessage());
    }

    @Test
    @DisplayName("회원 정보 닉네임 업데이트 성공")
    void updateNicknameAfterFindMember_success() {
        // given
        Member member = saveMember();
        RequestNicknameUpdateDto updateDto = RequestNicknameUpdateDto.builder()
                .nickname("라면부자")
                .build();

        // when
        memberService.updateNicknameAfterFindMember(member.getId(), updateDto);

        // then
        Member updateMember = memberRepository.findById(member.getId()).get();
        assertEquals(member.getNickname(), updateMember.getNickname());
    }

    @Test
    @DisplayName("회원 닉네임 정보가 중복된 닉네임으로 업데이트 한 경우 실패 -> 에러 던짐")
    void updateNicknameAfterFindMember_fail() {
        // given
        Member member = saveMember();
        RequestNicknameUpdateDto updateDto = RequestNicknameUpdateDto.builder()
                .nickname(member.getNickname())
                .build();

        // when && then
        assertThatThrownBy(() -> memberService.updateNicknameAfterFindMember(member.getId(), updateDto))
                .isInstanceOf(DuplicateRequestException.class)
                .hasMessage(ErrorCode.REQUEST_DATA_DUPLICATED.getMessage());
    }

    @Test
    @DisplayName("회원 정보 삭제 테스트 성공")
    void removeMember_success() {
        // given
        Member member = saveMember();

        memberService.removeMember(member.getId());

        // when && then
        assertThatThrownBy(() -> memberRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage())))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(ErrorCode.NOT_FOUND_ENTITY.getMessage());
    }

    private Member createMember(RequestMemberSaveDto dto) {
        return Member.builder()
                .name(dto.getName())
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
    }

    private Member saveMember() {
        RequestMemberSaveDto dto = RequestDtoFactory.createMemberSaveRequestDto();
        Member member = createMember(dto);
        return memberRepository.save(member);
    }
}