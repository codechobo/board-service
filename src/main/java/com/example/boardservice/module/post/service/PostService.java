package com.example.boardservice.module.post.service;

import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.module.member.domain.Member;
import com.example.boardservice.module.member.domain.repository.MemberRepository;
import com.example.boardservice.module.post.domain.Post;
import com.example.boardservice.module.post.domain.repository.PostRepository;
import com.example.boardservice.module.post.web.dto.PostSaveRequestDto;
import com.example.boardservice.module.post.web.dto.PostSaveResponseDto;
import com.example.boardservice.module.post.web.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostSaveResponseDto savePost(PostSaveRequestDto postSaveRequestDto) {
        Member member = getEntity(searchAuthor(postSaveRequestDto),
                new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));

        Post post = postSaveRequestDto.toEntity();
        post.addAuthor(member.getNickname());

        Post savedPost = postRepository.save(post);
        return PostSaveResponseDto.builder()
                .post(savedPost)
                .build();
    }

    private Optional<Member> searchAuthor(PostSaveRequestDto postSaveRequestDto) {
        return memberRepository.findByNickname(postSaveRequestDto.getAuthor());
    }

    public PostSaveResponseDto findByPostTitle(String title) {
        Post post = getEntity(searchTitle(title),
                new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));

        return PostSaveResponseDto.builder()
                .post(post)
                .build();
    }

    private Optional<Post> searchTitle(String title) {
        return postRepository.findByTitle(title);
    }

    private <T> T getEntity(Optional<T> optional, RuntimeException runtimeException) {
        return optional.orElseThrow(() -> runtimeException);
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
}
