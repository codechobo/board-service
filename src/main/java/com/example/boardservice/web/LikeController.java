package com.example.boardservice.web;

import com.example.boardservice.service.LikeService;
import com.example.boardservice.web.dto.like_dto.CommentLikeRequestDto;
import com.example.boardservice.web.dto.like_dto.CommentLikeResponseDto;
import com.example.boardservice.web.dto.like_dto.PostLikeRequestDto;
import com.example.boardservice.web.dto.like_dto.PostsLikeResponseDto;
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

    @PostMapping("/posts/likes")
    public ResponseEntity<PostsLikeResponseDto> createPostLike(
            @Valid @RequestBody PostLikeRequestDto postLikeRequestDto) {
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


}
