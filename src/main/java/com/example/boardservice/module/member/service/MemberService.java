package com.example.boardservice.module.member.service;

import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.module.member.domain.Member;
import com.example.boardservice.module.member.domain.repository.MemberRepository;
import com.example.boardservice.module.member.exception.NotMatchPasswordException;
import com.example.boardservice.module.member.web.dto.request.RequestNicknameUpdateDto;
import com.example.boardservice.module.member.web.dto.request.RequestPasswordUpdateDto;
import com.example.boardservice.module.member.web.dto.request.RequestMemberSaveDto;
import com.example.boardservice.module.member.web.dto.response.ResponseMemberSaveDto;
import com.example.boardservice.module.member.web.dto.response.ResponseMemberListDto;
import com.example.boardservice.module.member.web.dto.response.ResponseMembersPageDto;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseMemberSaveDto saveMember(RequestMemberSaveDto requestDto) {
        duplicatedCheck(requestDto);

        Member member = Member.createMember(requestDto);
        member.updatePassword(passwordEncoder.encode(requestDto.getPassword()));

        Member saveMember = memberRepository.save(member);
        return ResponseMemberSaveDto.builder().member(saveMember).build();
    }


    private void duplicatedCheck(RequestMemberSaveDto requestDto) {
        if (isExists(requestDto)) {
            throw new DuplicateRequestException(ErrorCode.REQUEST_DATA_DUPLICATED.getMessage());
        }
    }

    private boolean isExists(RequestMemberSaveDto requestDto) {
        return memberRepository.existsByNicknameOrPasswordOrEmail(
                requestDto.getNickname(),
                passwordEncoder.encode(requestDto.getPassword()), requestDto.getEmail());
    }

    public ResponseMemberSaveDto findMemberById(Long memberId) {
        Member member = getMemberEntity(memberId);
        return ResponseMemberSaveDto.builder().member(member).build();
    }

    public ResponseMembersPageDto getMemberListIncludingLastJoin(String searchName, Pageable pageable) {
        Page<ResponseMemberListDto> membersIncludingLastJoin =
                memberRepository.getMembersIncludingLastJoin(searchName, pageable);
        return ResponseMembersPageDto.toMapper(membersIncludingLastJoin);
    }

    @Transactional
    public void updatePasswordAfterFindMember(Long memberId, RequestPasswordUpdateDto passwordUpdateDto) {
        Member member = getMemberEntity(memberId);
        passwordMatch(passwordUpdateDto);
        member.updatePassword(passwordEncoder.encode(passwordUpdateDto.getNewPassword()));
    }

    private void passwordMatch(RequestPasswordUpdateDto passwordUpdateDto) {
        if (!isMatch(passwordUpdateDto)) {
            throw new NotMatchPasswordException(ErrorCode.NOT_MATCH_PASSWORD);
        }
    }

    private static boolean isMatch(RequestPasswordUpdateDto passwordUpdateDto) {
        return passwordUpdateDto.getNewPassword().equals(passwordUpdateDto.getNewPasswordConfirm());
    }

    @Transactional
    public void updateNicknameAfterFindMember(Long memberId, RequestNicknameUpdateDto requestNicknameUpdateDto) {
        if (isDuplicatedNicknameCheck(requestNicknameUpdateDto)) {
            throw new DuplicateRequestException(ErrorCode.REQUEST_DATA_DUPLICATED.getMessage());
        }

        Member member = getMemberEntity(memberId);
        member.updateNickname(requestNicknameUpdateDto.getNickname());
    }

    private boolean isDuplicatedNicknameCheck(RequestNicknameUpdateDto requestNicknameUpdateDto) {
        return memberRepository.existsByNickname(requestNicknameUpdateDto.getNickname());
    }

    @Transactional
    public void removeMember(Long memberId) {
        Member memberEntity = getMemberEntity(memberId);
        memberRepository.delete(memberEntity);
    }

    private Member getMemberEntity(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }
}
