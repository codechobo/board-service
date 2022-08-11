package com.example.boardservice.web;

import com.example.boardservice.service.CommentService;
import com.example.boardservice.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{posts-id}/comments")
    public ResponseEntity<CommentSaveResponseDto> createComment(
            @PathVariable("posts-id") Long postId,
            @Valid @RequestBody CommentSaveRequestDto commentSaveRequestDto) {
        CommentSaveResponseDto commentSaveResponseDto = commentService.saveComment(postId, commentSaveRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentSaveResponseDto);
    }

    @PostMapping("/posts/{posts-id}/comments/{comments-id}")
    public ResponseEntity<CommentOfCommentResponseDto> createCommentOfComment(
            @PathVariable("posts-id") Long postId,
            @PathVariable("comments-id") Long commentId,
            @Valid @RequestBody CommentOfCommentRequestDto commentOfCommentRequestDto) {
        CommentOfCommentResponseDto commentOfCommentResponseDto =
                commentService.saveCommentOfComment(postId, commentId, commentOfCommentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentOfCommentResponseDto);
    }

    @GetMapping("/posts/{posts-id}/comments/{comments-id}")
    public ResponseEntity<CommentSaveResponseDto> readCommentById(
            @PathVariable("posts-id") Long postId,
            @PathVariable("comments-id") Long commentId) {
        CommentSaveResponseDto commentSaveResponseDto = commentService.findCommentById(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(commentSaveResponseDto);
    }


    @PutMapping("/posts/comments/{comments-id}")
    public ResponseEntity<Void> updateComment(
            @PathVariable("comments-id") Long commentId,
            @RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
        commentService.updateAfterFindComment(commentId, commentUpdateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
