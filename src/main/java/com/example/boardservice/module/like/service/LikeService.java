package com.example.boardservice.module.like.service;

import com.example.boardservice.module.comment.domain.Comment;
import com.example.boardservice.module.comment.domain.repository.CommentRepository;
import com.example.boardservice.module.like.domain.Like;
import com.example.boardservice.module.member.domain.Member;
import com.example.boardservice.module.like.domain.repository.LikeRepository;
import com.example.boardservice.module.like.web.dto.request.CommentLikeRequestDto;
import com.example.boardservice.module.like.web.dto.request.PostLikeRequestDto;
import com.example.boardservice.module.like.web.dto.response.PostsLikeResponseDto;
import com.example.boardservice.module.member.domain.repository.MemberRepository;
import com.example.boardservice.module.post.domain.Post;
import com.example.boardservice.module.post.domain.repository.PostRepository;
import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.module.like.web.dto.response.CommentLikeResponseDto;
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

    public PostsLikeResponseDto savePostLike(PostLikeRequestDto postLikeRequestDto) {
        Member member = getMember(postLikeRequestDto.getMemberId());
        Post post = getPost(postLikeRequestDto);

        Like like = Like.builder()
                .membersId(member.getId())
                .postsId(post.getId())
                .build();

        likeRepository.save(like);
        return PostsLikeResponseDto.builder().like(like).build();
    }

    public CommentLikeResponseDto saveCommentLike(CommentLikeRequestDto commentLikeRequestDto) {
        Member member = getMember(commentLikeRequestDto.getMemberId());
        Comment comment = getComment(commentLikeRequestDto);

        Like like = Like.builder()
                .membersId(member.getId())
                .commentsId(comment.getId())
                .build();
        likeRepository.save(like);
        return CommentLikeResponseDto.builder().like(like).build();
    }

    @Transactional
    public void removePostLike(PostLikeRequestDto postLikeRequestDto) {
        Like like = getLikeWithMemberIdAndPostId(postLikeRequestDto.getMemberId(), postLikeRequestDto.getPostId());
        likeRepository.delete(like);
    }

    @Transactional
    public void removeCommentLike(CommentLikeRequestDto commentLikeRequestDto) {
        Like like = getLikeWithMemberIdAndCommentId(commentLikeRequestDto);
        likeRepository.delete(like);
    }


    private Post getPost(PostLikeRequestDto postLikeRequestDto) {
        return postRepository.findById(postLikeRequestDto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }

    private Comment getComment(CommentLikeRequestDto commentLikeRequestDto) {
        return commentRepository.findById(commentLikeRequestDto.getCommentId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }

    private Like getLikeWithMemberIdAndPostId(Long memberId, Long postId) {
        return likeRepository.findByMembersIdAndPostsId(
                        memberId, postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }

    private Like getLikeWithMemberIdAndCommentId(CommentLikeRequestDto commentLikeRequestDto) {
        return likeRepository.findByMembersIdAndCommentsId(commentLikeRequestDto.getMemberId(), commentLikeRequestDto.getCommentId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }
}
