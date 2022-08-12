package com.example.boardservice.service;

import com.example.boardservice.domain.Like;
import com.example.boardservice.domain.Member;
import com.example.boardservice.domain.Post;
import com.example.boardservice.domain.repository.LikeRepository;
import com.example.boardservice.domain.repository.MemberRepository;
import com.example.boardservice.domain.repository.PostRepository;
import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.web.dto.LikeRequestDto;
import com.example.boardservice.web.dto.LikeResponseDto;
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

    @Transactional
    public LikeResponseDto saveLike(LikeRequestDto likeRequestDto) {
        Member member = memberRepository.findById(likeRequestDto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));

        Post post = postRepository.findById(likeRequestDto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));

        Like like = Like.builder()
                .membersId(member.getId())
                .postsId(post.getId())
                .build();
        likeRepository.save(like);

        return LikeResponseDto.builder()
                .like(like)
                .build();
    }

    @Transactional
    public void removeLike(LikeRequestDto likeRequestDto) {
        Like like = likeRepository.findByMembersIdAndPostsId(likeRequestDto.getMemberId(), likeRequestDto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));

        likeRepository.delete(like);
    }
}
