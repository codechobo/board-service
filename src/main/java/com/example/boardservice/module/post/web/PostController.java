package com.example.boardservice.module.post.web;

import com.example.boardservice.module.post.service.PostService;
import com.example.boardservice.module.post.web.dto.PostSaveRequestDto;
import com.example.boardservice.module.post.web.dto.PostSaveResponseDto;
import com.example.boardservice.module.post.web.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostSaveResponseDto> readPostById(
            @PathVariable("id") Long postId) {
        PostSaveResponseDto postSaveResponseDto = postService.findByPostId(postId);
        return ResponseEntity.status(HttpStatus.OK).body(postSaveResponseDto);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostSaveResponseDto>> readPosts() {
        List<PostSaveResponseDto> posts = postService.findPosts();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Void> updatePost(
            @PathVariable("id") Long postId,
            @RequestBody PostUpdateRequestDto postUpdateRequestDto) {
        postService.updateAfterFindPost(postId, postUpdateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long postId) {
        postService.removePost(postId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
