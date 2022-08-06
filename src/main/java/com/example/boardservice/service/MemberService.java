package com.example.boardservice.service;

import com.example.boardservice.domain.Member;
import com.example.boardservice.domain.repository.MemberRepository;
import com.example.boardservice.web.dto.MemberSaveRequestDto;
import com.example.boardservice.web.dto.MemberSaveResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberSaveResponseDto saveMember(MemberSaveRequestDto memberSaveRequestDto) {
        duplicatedInfoCheck(memberSaveRequestDto);

        Member member = memberRepository.save(memberSaveRequestDto.toEntity());

        return MemberSaveResponseDto.builder()
                .member(member)
                .build();
    }

    private void duplicatedInfoCheck(MemberSaveRequestDto memberSaveRequestDto) {
        if (memberRepository.existsByNickname(memberSaveRequestDto.getNickname())) {
            throw new IllegalArgumentException("중복 정보");
        }

        if (memberRepository.existsByEmail(memberSaveRequestDto.getEmail())) {
            throw new IllegalArgumentException("중복 정보");
        }

        if (memberRepository.existsByPassword(memberSaveRequestDto.getPassword())) {
            throw new IllegalArgumentException("중복 정보");
        }
    }

    public MemberSaveResponseDto findMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않은 아이디입니다."));
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
}
