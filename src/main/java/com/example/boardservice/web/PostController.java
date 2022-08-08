package com.example.boardservice.web;

import com.example.boardservice.service.PostService;
import com.example.boardservice.web.dto.PostSaveRequestDto;
import com.example.boardservice.web.dto.PostSaveResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostSaveResponseDto> createPost(
            @RequestBody PostSaveRequestDto postSaveRequestDto) {
        PostSaveResponseDto postSaveResponseDto = postService.savePost(postSaveRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(postSaveResponseDto);
    }
}
