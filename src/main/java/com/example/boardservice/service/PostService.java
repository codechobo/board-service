package com.example.boardservice.service;

import com.example.boardservice.domain.Member;
import com.example.boardservice.domain.Post;
import com.example.boardservice.domain.repository.MemberRepository;
import com.example.boardservice.domain.repository.PostRepository;
import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.web.dto.post_dto.PostSaveRequestDto;
import com.example.boardservice.web.dto.post_dto.PostSaveResponseDto;
import com.example.boardservice.web.dto.post_dto.PostUpdateRequestDto;
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

    public PostSaveResponseDto findByPostId(Long postId) {
        Post entity = getEntity(postId);
        return PostSaveResponseDto.builder().post(entity).build();
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

    private Member checkExistMember(PostSaveRequestDto postSaveRequestDto) {
        return memberRepository.findByNickname(postSaveRequestDto.getAuthor())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }
}
