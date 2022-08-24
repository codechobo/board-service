package com.example.boardservice.module.post.service;

import com.example.boardservice.module.member.domain.Member;
import com.example.boardservice.module.post.domain.Post;
import com.example.boardservice.module.member.domain.repository.MemberRepository;
import com.example.boardservice.module.post.domain.repository.PostRepository;
import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.module.post.web.post_model.PostSaveRequestDto;
import com.example.boardservice.module.post.web.post_model.PostSaveResponseDto;
import com.example.boardservice.module.post.web.post_model.PostUpdateRequestDto;
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

        Post savedPost = postRepository.save(post);

        return PostSaveResponseDto.builder().post(savedPost).build();
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
