package com.example.boardservice.web;

import com.example.boardservice.service.CommentService;
import com.example.boardservice.web.dto.CommentOfCommentResponseDto;
import com.example.boardservice.web.dto.CommentSaveRequestDto;
import com.example.boardservice.web.dto.CommentSaveResponseDto;
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

    @PostMapping("/{post-id}/comments")
    public ResponseEntity<CommentSaveResponseDto> createComment(
            @PathVariable("post-id") Long postId,
            @Valid @RequestBody CommentSaveRequestDto commentSaveRequestDto) {
        CommentSaveResponseDto commentSaveResponseDto = commentService.saveComment(postId, commentSaveRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentSaveResponseDto);
    }

    @PostMapping("/{post-id}/comments/{comment-id}")
    public ResponseEntity<CommentOfCommentResponseDto> createCommentOfComment(
            @PathVariable("post-id") Long postId,
            @PathVariable("comment-id") Long commentId,
            @Valid @RequestBody CommentOfCommentRequestDto commentOfCommentRequestDto) {
        CommentOfCommentResponseDto commentOfCommentResponseDto =
                commentService.saveCommentOfComment(postId, commentId, commentOfCommentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentOfCommentResponseDto);
    }
}
