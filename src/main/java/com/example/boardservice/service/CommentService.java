package com.example.boardservice.service;

import com.example.boardservice.domain.Comment;
import com.example.boardservice.domain.Member;
import com.example.boardservice.domain.Post;
import com.example.boardservice.domain.repository.MemberRepository;
import com.example.boardservice.domain.repository.PostRepository;
import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.web.CommentOfCommentRequestDto;
import com.example.boardservice.web.dto.CommentOfCommentResponseDto;
import com.example.boardservice.web.dto.CommentSaveRequestDto;
import com.example.boardservice.web.dto.CommentSaveResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public CommentSaveResponseDto saveComment(Long postId, CommentSaveRequestDto commentSaveRequestDto) {
        String authorNickname = commentSaveRequestDto.getAuthor();
        Member member = memberRepository.findByNickname(authorNickname)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));

        Comment comment = Comment.builder()
                .author(member.getNickname())
                .content(commentSaveRequestDto.getContent())
                .build();
        comment.addPost(post);

        commentRepository.save(comment);

        return CommentSaveResponseDto.builder()
                .title(post.getTitle())
                .author(comment.getAuthor())
                .content(comment.getContent())
                .build();
    }

    @Transactional
    public CommentOfCommentResponseDto saveCommentOfComment(
            Long postId,
            Long commentId,
            CommentOfCommentRequestDto commentOfCommentRequestDto) {

        String authorNickname = commentOfCommentRequestDto.getAuthor();
        Member member = memberRepository.findByNickname(authorNickname)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));

        Post post = comment.getPost();
        Comment commentOfComment = Comment.builder()
                .author(member.getNickname())
                .content(commentOfCommentRequestDto.getContent())
                .build();
        comment.addComment(commentOfComment);

        return CommentOfCommentResponseDto.builder()
                .title(post.getTitle())
                .author(member.getNickname())
                .content(post.getContent())
                .build();
    }
}
