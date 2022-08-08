package com.example.boardservice.service;

import com.example.boardservice.domain.Member;
import com.example.boardservice.domain.repository.MemberRepository;
import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.web.dto.MemberSaveRequestDto;
import com.example.boardservice.web.dto.MemberSaveResponseDto;
import com.example.boardservice.web.dto.MemberUpdateRequestDto;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberSaveResponseDto saveMember(MemberSaveRequestDto requestDto) {
        duplicatedCheck(requestDto.getNickname(), requestDto.getEmail(), requestDto.getPassword());

        Member member = memberRepository.save(requestDto.toEntity());

        return MemberSaveResponseDto.builder()
                .member(member)
                .build();
    }

    public MemberSaveResponseDto findMemberById(Long memberId) {
        Member member = getEntity(memberId);
        return MemberSaveResponseDto.builder()
                .member(member)
                .build();
    }

    public List<MemberSaveResponseDto> findMembers() {
        return memberRepository.findAll().stream()
                .map(member -> MemberSaveResponseDto.builder()
                        .member(member)
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateAfterFindMember(MemberUpdateRequestDto requestDto) {
        Member entity = getEntity(requestDto.getMemberId());

        duplicatedCheck(requestDto.getNickname(), requestDto.getEmail(), requestDto.getPassword());

        entity.updateNickname(requestDto.getNickname());
        entity.updateEmail(requestDto.getEmail());
        entity.updatePassword(requestDto.getPassword());
    }

    private Member getEntity(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }

    private void duplicatedCheck(String nickname, String email, String password) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new DuplicateRequestException(ErrorCode.REQUEST_DATA_DUPLICATED.getMessage());
        }

        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateRequestException(ErrorCode.REQUEST_DATA_DUPLICATED.getMessage());
        }

        if (memberRepository.existsByPassword(password)) {
            throw new DuplicateRequestException(ErrorCode.REQUEST_DATA_DUPLICATED.getMessage());
        }
    }
}
