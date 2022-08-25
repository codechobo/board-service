package com.example.boardservice.module.member.service;

import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.module.member.domain.Member;
import com.example.boardservice.module.member.domain.repository.MemberRepository;
import com.example.boardservice.module.member.web.dto.request.MemberSaveRequestDto;
import com.example.boardservice.module.member.web.dto.request.MemberUpdateRequestDto;
import com.example.boardservice.module.member.web.dto.response.MemberSaveResponseDto;
import com.example.boardservice.module.member.web.dto.response.ResponseMemberListDto;
import com.example.boardservice.module.member.web.dto.response.ResponseMembersPageDto;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    /***
     * @param requestDto
     * @return memberResponseDto
     * 닉네임, 이메일, 패스워드 중복체크를 거친 다음 세이브
     */
    public MemberSaveResponseDto saveMember(MemberSaveRequestDto requestDto) {
        duplicatedCheck(requestDto.getNickname(), requestDto.getEmail(), requestDto.getPassword());

        Member member = memberRepository.save(requestDto.toEntity());
        return MemberSaveResponseDto.builder().member(member).build();
    }

    /***
     * @param memberId
     * @return memberSaveResponseDto
     * 엔티티가 존재하지 않는 경우 DuplicateRequestException 예외 발생
     * 존재 한다면 엔티티를 가져와 Dto 로 변환 후 반환
     */
    public MemberSaveResponseDto findMemberById(Long memberId) {
        Member member = getMemberEntity(memberId);
        return MemberSaveResponseDto.builder().member(member).build();
    }

    /**
     * @param searchName
     * @param pageable
     * @return
     *  이름으로 조회하여 리스트를 받는다.
     *  리스트는 최근 회원 가입 된 기준으로 정렬된다.
     */
    public ResponseMembersPageDto getMemberListIncludingLastJoin(String searchName, Pageable pageable) {
        Page<ResponseMemberListDto> membersIncludingLastJoin =
                memberRepository.getMembersIncludingLastJoin(searchName, pageable);
        return ResponseMembersPageDto.toMapper(membersIncludingLastJoin);
    }

    /**
     * @param requestDto 1. 멤버 엔티티가 있는 경우 -> 예외 생략...
     *                   2. 중복체크를 하고 -> 예외 생략 ..
     *                   3. 엔티티의 업데이트 메소드로 데이터를 변경하여
     *                   4. 더티 체킹이 발생 되도록
     */
    @Transactional
    public void updateAfterFindMember(MemberUpdateRequestDto requestDto) {
        Member entity = getMemberEntity(requestDto.getMemberId());
        duplicatedCheck(requestDto.getNickname(), requestDto.getEmail(), requestDto.getPassword());

        entity.updateNickname(requestDto.getNickname());
        entity.updateEmail(requestDto.getEmail());
        entity.updatePassword(requestDto.getPassword());
    }

    /**
     * @param memberId
     * param 으로 넘어온 id 값으로 엔티티를 찾아와서 해당 엔티티를 delete 한다.
     */
    @Transactional
    public void removeMember(Long memberId) {
        Member memberEntity = getMemberEntity(memberId);
        memberRepository.delete(memberEntity);
    }

    private Member getMemberEntity(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }

    private void duplicatedCheck(String nickname, String email, String password) {
        if (isExists(nickname, email, password)) {
            throw new DuplicateRequestException(ErrorCode.REQUEST_DATA_DUPLICATED.getMessage());
        }
    }

    private boolean isExists(String nickname, String email, String password) {
        return memberRepository.existsByNicknameAndEmailAndPassword(nickname, email, password);
    }
}
