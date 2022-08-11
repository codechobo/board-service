package com.example.boardservice.service;

import com.example.boardservice.domain.Comment;
import com.example.boardservice.domain.Member;
import com.example.boardservice.domain.Post;
import com.example.boardservice.domain.repository.MemberRepository;
import com.example.boardservice.domain.repository.PostRepository;
import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.web.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public CommentSaveResponseDto saveComment(Long postId, CommentSaveRequestDto commentSaveRequestDto) {
        Member member = getMemberEntity(commentSaveRequestDto.getAuthor());
        Post post = getPostEntity(postId);

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
    public CommentOfCommentResponseDto saveCommentOfComment(Long postId, Long commentId,
                                                            CommentOfCommentRequestDto commentOfCommentRequestDto) {
        Member member = getMemberEntity(commentOfCommentRequestDto.getAuthor());
        Post post = getPostEntity(postId);
        Comment comment = getCommentEntity(commentId);

        Comment commentOfComment = Comment.builder()
                .author(member.getNickname())
                .content(commentOfCommentRequestDto.getContent())
                .build();
        commentOfComment.addPost(post);
        comment.addComment(post, commentOfComment);

        return CommentOfCommentResponseDto.builder()
                .comment(comment)
                .build();
    }

    public CommentSaveResponseDto findCommentById(Long commentId) {
        Comment comment = getCommentEntity(commentId);

        return CommentSaveResponseDto.builder()
                .author(comment.getAuthor())
                .title(comment.getPost().getTitle())
                .content(comment.getContent())
                .build();
    }

    @Transactional
    public void updateAfterFindComment(Long commentId,
                                       CommentUpdateRequestDto commentUpdateRequestDto) {
        Comment comment = getCommentEntity(commentId);
        comment.updateContent(commentUpdateRequestDto.getContent());
    }

    @Transactional
    public void removeComment(Long commentId) {
        Comment entity = getCommentEntity(commentId);
        commentRepository.delete(entity);
    }

    private Member getMemberEntity(String authorNickname) {
        return memberRepository.findByNickname(authorNickname)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }

    private Post getPostEntity(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }

    private Comment getCommentEntity(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }
}
