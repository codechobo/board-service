package com.example.boardservice.web;

import com.example.boardservice.service.LikeService;
import com.example.boardservice.web.dto.LikeRequestDto;
import com.example.boardservice.web.dto.LikeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/likes")
    public ResponseEntity<LikeResponseDto> createLike(
            @Valid @RequestBody LikeRequestDto likeRequestDto) {
        LikeResponseDto likeResponseDto = likeService.saveLike(likeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(likeResponseDto);
    }

    @DeleteMapping("/likes")
    public ResponseEntity<Void> deleteLike(
            @Valid @RequestBody LikeRequestDto likeRequestDto) {
        likeService.removeLike(likeRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
