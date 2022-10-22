package com.example.boardservice.module.like.web;

import com.example.boardservice.module.like.service.LikeService;
import com.example.boardservice.module.like.web.dto.request.CommentLikeRequestDto;
import com.example.boardservice.module.like.web.dto.response.CommentLikeResponseDto;
import com.example.boardservice.module.like.web.dto.request.PostLikeRequestDto;
import com.example.boardservice.module.like.web.dto.response.PostsLikeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/posts/likes")
    public ResponseEntity<PostsLikeResponseDto> createPostLike(
            @Valid @RequestBody(required = false) PostLikeRequestDto postLikeRequestDto) {
        PostsLikeResponseDto postsLikeResponseDto = likeService.savePostLike(postLikeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(postsLikeResponseDto);
    }

    @PostMapping("/comments/likes")
    public ResponseEntity<CommentLikeResponseDto> createCommentLike(
            @Valid @RequestBody CommentLikeRequestDto commentLikeRequestDto) {
        CommentLikeResponseDto commentLikeResponseDto = likeService.saveCommentLike(commentLikeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentLikeResponseDto);
    }

    @DeleteMapping("/posts/likes")
    public ResponseEntity<Void> deletePostLike(
            @Valid @RequestBody PostLikeRequestDto postLikeRequestDto) {
        likeService.removePostLike(postLikeRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/comments/likes")
    public ResponseEntity<Void> deleteCommentLike(
            @Valid @RequestBody CommentLikeRequestDto commentLikeRequestDto) {
        likeService.removeCommentLike(commentLikeRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
