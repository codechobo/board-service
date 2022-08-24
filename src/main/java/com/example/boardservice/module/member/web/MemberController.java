package com.example.boardservice.module.member.web;

import com.example.boardservice.module.member.service.MemberService;
import com.example.boardservice.member.web.model.*;
import com.example.boardservice.module.member.web.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<MemberSaveResponseDto> createMember(
            @Valid @RequestBody MemberSaveRequestDto memberSaveRequestDto) {
        MemberSaveResponseDto memberSaveResponseDto =
                memberService.saveMember(memberSaveRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberSaveResponseDto);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberSaveResponseDto> readMemberById(
            @PathVariable("id") Long memberId) {
        MemberSaveResponseDto memberSaveResponseDto = memberService.findMemberById(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(memberSaveResponseDto);
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberSaveResponseDto>> readMembers() {
        List<MemberSaveResponseDto> members = memberService.findMembers();
        return ResponseEntity.status(HttpStatus.OK).body(members);
    }

    @PutMapping("/members")
    public ResponseEntity<Void> updateMember(
            @Valid @RequestBody MemberUpdateRequestDto memberUpdateRequestDto) {
        memberService.updateAfterFindMember(memberUpdateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") Long memberId) {
        memberService.removeMember(memberId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/members/login")
    public ResponseEntity<MemberAuthResponseDto> checkedLoginInfo(
            @RequestBody MemberLoginForm memberLoginForm) {
        MemberAuthResponseDto memberAuthResponseDto = memberService
                .getAuthInfoAfterVerifyingLoginInfo(memberLoginForm);

        return ResponseEntity.status(HttpStatus.OK).body(memberAuthResponseDto);
    }

}
