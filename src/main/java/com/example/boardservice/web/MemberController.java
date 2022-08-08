package com.example.boardservice.web;

import com.example.boardservice.service.MemberService;
import com.example.boardservice.web.dto.MemberSaveRequestDto;
import com.example.boardservice.web.dto.MemberSaveResponseDto;
import com.example.boardservice.web.dto.MemberUpdateRequestDto;
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

}
