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
public class PostController {

    private final PostService postService;

    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostSaveResponseDto> createPost(
            @RequestBody PostSaveRequestDto postSaveRequestDto) {
        PostSaveResponseDto postSaveResponseDto = postService.savePost(postSaveRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(postSaveResponseDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostSaveResponseDto> readPostById(
            @PathVariable("id") Long postId) {
        PostSaveResponseDto postSaveResponseDto = postService.findByPostId(postId);
        return ResponseEntity.status(HttpStatus.OK).body(postSaveResponseDto);
    }

    /**
     *  전체를 가져오는 기능인데 정렬 기능과 페이징 기능이 없다. 어떤 기준으로 검색해서 가져오는 것인지 없다.
     *
     *  TODO
     *  1. 게시판 글을 최신순으로 조회하는 기능
     *  2. 작성자 이름으로 게시글을 조회하는 기능 -> 정렬 최신순
     *  3. 제목의 키워드 중심으로 조회하는 기능 -> 정렬 최신순
     *  4. 본문 내용 즉, 게시글의 내용의 중심으로 조회하는 기능 -> 정렬 최신순
     *
     */

    // TODO
    //  전체 리스트를 최신순으로 바꿔보자
    @GetMapping("/api/v1/posts")
    public ResponseEntity<List<PostSaveResponseDto>> readPosts() {
        List<PostSaveResponseDto> posts = postService.findPosts();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @PutMapping("/api/v1/posts/{id}")
    public ResponseEntity<Void> updatePost(
            @PathVariable("id") Long postId,
            @RequestBody PostUpdateRequestDto postUpdateRequestDto) {
        postService.updateAfterFindPost(postId, postUpdateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long postId) {
        postService.removePost(postId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
