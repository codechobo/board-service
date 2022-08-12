package com.example.boardservice.service;

import com.example.boardservice.domain.Like;
import com.example.boardservice.domain.Member;
import com.example.boardservice.domain.Post;
import com.example.boardservice.domain.repository.LikeRepository;
import com.example.boardservice.domain.repository.MemberRepository;
import com.example.boardservice.domain.repository.PostRepository;
import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.web.dto.LikeSaveRequestDto;
import com.example.boardservice.web.dto.LikeSaveResponseDto;
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
    public LikeSaveResponseDto saveLike(LikeSaveRequestDto likeSaveRequestDto) {
        Member member = memberRepository.findById(likeSaveRequestDto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));

        Post post = postRepository.findById(likeSaveRequestDto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));

        Like like = Like.builder()
                .membersId(member.getId())
                .postsId(post.getId())
                .build();
        likeRepository.save(like);

        return LikeSaveResponseDto.builder()
                .like(like)
                .build();
    }
}
