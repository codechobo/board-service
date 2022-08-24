package com.example.boardservice.module.comment.web;

import com.example.boardservice.module.comment.service.CommentService;
import com.example.boardservice.comment.web.comment_model.*;
import com.example.boardservice.module.comment.web.comment_model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/posts/comments/{comments-id}")
    public ResponseEntity<CommentSaveResponseDto> readCommentById(
            @PathVariable("comments-id") Long commentId) {
        CommentSaveResponseDto commentSaveResponseDto = commentService.findCommentById(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(commentSaveResponseDto);
    }

    @GetMapping("/posts/comments")
    public ResponseEntity<List<CommentSaveResponseDto>> readComments() {
        List<CommentSaveResponseDto> commentSaveResponseDtos = commentService.findComments();
        return ResponseEntity.status(HttpStatus.OK).body(commentSaveResponseDtos);
    }

    @PutMapping("/posts/comments/{comments-id}")
    public ResponseEntity<Void> updateComment(
            @PathVariable("comments-id") Long commentId,
            @RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
        commentService.updateAfterFindComment(commentId, commentUpdateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/posts/comments")
    public ResponseEntity<Void> deleteComment(
            @RequestParam("comments-id") Long commentId) {
        commentService.removeComment(commentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
