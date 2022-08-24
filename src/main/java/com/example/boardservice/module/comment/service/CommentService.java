package com.example.boardservice.module.comment.service;

import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.module.comment.domain.Comment;
import com.example.boardservice.module.comment.domain.repository.CommentRepository;
import com.example.boardservice.module.comment.web.comment_model.*;
import com.example.boardservice.module.member.domain.Member;
import com.example.boardservice.module.member.domain.repository.MemberRepository;
import com.example.boardservice.module.post.domain.Post;
import com.example.boardservice.module.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

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

        Comment saveComment = commentRepository.save(comment);

        return CommentSaveResponseDto.builder()
                .author(saveComment.getAuthor())
                .content(saveComment.getContent())
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

    public List<CommentSaveResponseDto> findComments() {
        return commentRepository.findAll().stream()
                .map(comment -> CommentSaveResponseDto.builder()
                        .author(comment.getAuthor())
                        .content(comment.getContent())
                        .build())
                .collect(Collectors.toList());
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