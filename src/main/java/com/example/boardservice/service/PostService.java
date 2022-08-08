package com.example.boardservice.service;

import com.example.boardservice.domain.Member;
import com.example.boardservice.domain.Post;
import com.example.boardservice.domain.repository.MemberRepository;
import com.example.boardservice.domain.repository.PostRepository;
import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.web.dto.PostSaveRequestDto;
import com.example.boardservice.web.dto.PostSaveResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public PostSaveResponseDto savePost(PostSaveRequestDto postSaveRequestDto) {
        Member member = checkExistMember(postSaveRequestDto);

        Post post = postSaveRequestDto.toEntity();
        post.addAuthor(member.getNickname());
        postRepository.save(post);

        return PostSaveResponseDto.builder().post(post).build();
    }

    private Member checkExistMember(PostSaveRequestDto postSaveRequestDto) {
        Member member = memberRepository.findByNickname(postSaveRequestDto.getAuthor())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
        return member;
    }
}
