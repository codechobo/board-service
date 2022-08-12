package com.example.boardservice.web;

import com.example.boardservice.service.LikeService;
import com.example.boardservice.web.dto.LikeSaveRequestDto;
import com.example.boardservice.web.dto.LikeSaveResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/likes")
    public ResponseEntity<LikeSaveResponseDto> createLike(
            @Valid @RequestBody LikeSaveRequestDto likeSaveRequestDto) {
        LikeSaveResponseDto likeSaveResponseDto = likeService.saveLike(likeSaveRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(likeSaveResponseDto);
    }

}
