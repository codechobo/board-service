package com.example.boardservice.service;

import com.example.boardservice.domain.Comment;
import com.example.boardservice.domain.Like;
import com.example.boardservice.domain.Member;
import com.example.boardservice.domain.Post;
import com.example.boardservice.domain.repository.LikeRepository;
import com.example.boardservice.domain.repository.MemberRepository;
import com.example.boardservice.domain.repository.PostRepository;
import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.web.dto.like_dto.CommentLikeRequestDto;
import com.example.boardservice.web.dto.like_dto.CommentLikeResponseDto;
import com.example.boardservice.web.dto.like_dto.PostLikeRequestDto;
import com.example.boardservice.web.dto.like_dto.PostsLikeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public PostsLikeResponseDto savePostLike(PostLikeRequestDto postLikeRequestDto) {
        Member member = memberRepository.findById(postLikeRequestDto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));

        Post post = postRepository.findById(postLikeRequestDto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));

        Like like = Like.builder()
                .membersId(member.getId())
                .postsId(post.getId())
                .build();
        likeRepository.save(like);

        return PostsLikeResponseDto.builder()
                .like(like)
                .build();
    }

    @Transactional
    public void removePostLike(PostLikeRequestDto postLikeRequestDto) {
        Like like = likeRepository.findByMembersIdAndPostsId(
                        postLikeRequestDto.getMemberId(), postLikeRequestDto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
        likeRepository.delete(like);
    }

    @Transactional
    public CommentLikeResponseDto saveCommentLike(CommentLikeRequestDto commentLikeRequestDto) {
        Member member = memberRepository.findById(commentLikeRequestDto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));

        Comment comment = commentRepository.findById(commentLikeRequestDto.getCommentId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));

        Like like = Like.builder()
                .membersId(member.getId())
                .commentsId(comment.getId())
                .build();
        likeRepository.save(like);

        return CommentLikeResponseDto.builder()
                .like(like)
                .build();
    }
}
