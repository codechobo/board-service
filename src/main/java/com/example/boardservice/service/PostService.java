package com.example.boardservice.service;

import com.example.boardservice.domain.Member;
import com.example.boardservice.domain.Post;
import com.example.boardservice.domain.repository.MemberRepository;
import com.example.boardservice.domain.repository.PostRepository;
import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.web.dto.PostSaveRequestDto;
import com.example.boardservice.web.dto.PostSaveResponseDto;
import com.example.boardservice.web.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new EntityNotFoundException(
                        ErrorCode.NOT_FOUND_ENTITY.getMessage()));
        return member;
    }

    public PostSaveResponseDto findByPostId(Long postId) {
        Post entity = getEntity(postId);

        return PostSaveResponseDto.builder().post(entity).build();
    }

    private Post getEntity(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }

    public List<PostSaveResponseDto> findPosts() {
        return postRepository.findAll().stream()
                .map(post -> PostSaveResponseDto.builder()
                        .post(post)
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateAfterFindPost(Long postId, PostUpdateRequestDto postUpdateRequestDto) {
        Post entity = getEntity(postId);

        entity.updateTitle(postUpdateRequestDto.getTitle());
        entity.updateContent(postUpdateRequestDto.getContent());
    }

    @Transactional
    public void removePost(Long postId) {
        Post entity = getEntity(postId);
        
        postRepository.delete(entity);
    }
}
