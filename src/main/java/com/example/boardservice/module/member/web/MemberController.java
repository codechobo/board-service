package com.example.boardservice.module.member.web;

import com.example.boardservice.module.member.service.MemberService;
import com.example.boardservice.module.member.web.dto.request.RequestNicknameUpdateDto;
import com.example.boardservice.module.member.web.dto.request.RequestMemberSaveDto;
import com.example.boardservice.module.member.web.dto.request.RequestPasswordUpdateDto;
import com.example.boardservice.module.member.web.dto.response.ResponseMemberSaveDto;
import com.example.boardservice.module.member.web.dto.response.ResponseMembersPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<ResponseMemberSaveDto> createMember(@Valid @RequestBody RequestMemberSaveDto memberSaveRequestDto) {
        ResponseMemberSaveDto memberSaveResponseDto = memberService.saveMember(memberSaveRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberSaveResponseDto);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<ResponseMemberSaveDto> getMember(@PathVariable("id") Long memberId) {
        ResponseMemberSaveDto memberSaveResponseDto = memberService.findMemberById(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(memberSaveResponseDto);
    }

    @GetMapping("/members")
    public ResponseEntity<ResponseMembersPageDto> getMembers(@RequestParam(value = "name", required = false) String searchName,
                                                             @PageableDefault(size = 6) Pageable pageable) {
        ResponseMembersPageDto responseMembersPageDto = memberService.getMemberListIncludingLastJoin(searchName, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(responseMembersPageDto);
    }

    @PutMapping("/members/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable("id") Long memberId,
                                               @Valid @RequestBody RequestPasswordUpdateDto requestPasswordUpdateDto) {
        memberService.updatePasswordAfterFindMember(memberId, requestPasswordUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/members/{id}/nickname")
    public ResponseEntity<Void> updateNickname(@PathVariable("id") Long memberId,
                                               @Valid @RequestBody RequestNicknameUpdateDto requestNicknameUpdateDto) {
        memberService.updateNicknameAfterFindMember(memberId, requestNicknameUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") Long memberId) {
        memberService.removeMember(memberId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
