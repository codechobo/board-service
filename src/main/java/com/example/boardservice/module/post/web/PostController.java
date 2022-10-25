package com.example.boardservice.module.post.web;

import com.example.boardservice.module.hashtag.service.HashTagService;
import com.example.boardservice.module.hashtag.web.dto.ResponseHashTagListDto;
import com.example.boardservice.module.hashtag.web.dto.ResponseHashTagSaveDto;
import com.example.boardservice.module.post.service.PostService;
import com.example.boardservice.module.post.web.dto.ResponsePostWithHashTagSaveDto;
import com.example.boardservice.module.post.web.dto.request.RequestPostSaveDto;
import com.example.boardservice.module.post.web.dto.request.RequestPostUpdateDto;
import com.example.boardservice.module.post.web.dto.request.RequestSearchPostDto;
import com.example.boardservice.module.post.web.dto.response.ResponsePostPagingDto;
import com.example.boardservice.module.post.web.dto.response.ResponsePostSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final HashTagService hashTagService;

    @GetMapping("/posts/{id}/hash-tags")
    public ResponseEntity<ResponseHashTagListDto> getHashTags(@PathVariable("id") Long postsId) {
        ResponseHashTagListDto responseHashTagListDto = postService.findHashTags(postsId);
        return ResponseEntity.status(HttpStatus.OK).body(responseHashTagListDto);
    }

    @PostMapping("/posts")
    public ResponseEntity<ResponsePostWithHashTagSaveDto> createPost(@Valid @RequestBody(required = false) RequestPostSaveDto requestDto) {
        // 해쉬태그 저장
        ResponseHashTagSaveDto responseHashTagSaveDto = hashTagService.saveHashTag(requestDto.getHashTagNames());
        // 게시글 저장
        ResponsePostSaveDto responseDto = postService.savePost(requestDto);
        ResponsePostWithHashTagSaveDto dto = ResponsePostWithHashTagSaveDto.of(responseHashTagSaveDto, responseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/posts/{id}/close")
    public ResponseEntity<Void> closePost(@PathVariable("id") Long postId) {
        postService.close(postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<ResponsePostSaveDto> getPost(@PathVariable("id") Long postId) {
        ResponsePostSaveDto responseDto = postService.findByPostId(postId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/posts")
    public ResponseEntity<ResponsePostPagingDto> getPostList(@Valid @RequestBody(required = false) RequestSearchPostDto requestSearchPostDto,
                                                             @PageableDefault(size = 5) Pageable pageable) {
        ResponsePostPagingDto responsePagingDto = postService.findPosts(requestSearchPostDto, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(responsePagingDto);
    }

    @GetMapping("/posts/hits")
    public ResponseEntity<ResponsePostPagingDto> getPostListFormViewCount(@Valid @RequestBody RequestSearchPostDto requestSearchPostDto,
                                                                          @PageableDefault(size = 5) Pageable pageable) {
        ResponsePostPagingDto postsFromViewCount = postService.findPostsFromViewCount(requestSearchPostDto, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(postsFromViewCount);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable("id") Long postId, @RequestBody RequestPostUpdateDto requestDto) {
        postService.updateAfterFindPost(postId, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long postId) {
        postService.removePost(postId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}