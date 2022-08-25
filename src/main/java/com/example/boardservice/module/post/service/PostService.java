package com.example.boardservice.module.post.service;

import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.module.member.domain.Member;
import com.example.boardservice.module.member.domain.repository.MemberRepository;
import com.example.boardservice.module.post.domain.Post;
import com.example.boardservice.module.post.domain.repository.PostRepository;
import com.example.boardservice.module.post.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

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

    public PostSaveResponseDto findByPostId(Long postId) {
        Post post = getEntity(searchPostId(postId),
                new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
        return PostSaveResponseDto.builder()
                .post(post)
                .build();
    }

    @Transactional
    public void updateAfterFindPost(Long postId, PostUpdateRequestDto postUpdateRequestDto) {
        Post entity = getEntity(searchPostId(postId),
                new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
        entity.updateTitle(postUpdateRequestDto.getTitle());
        entity.updateContent(postUpdateRequestDto.getContent());
    }

    private Optional<Post> searchPostId(Long id) {
        return postRepository.findById(id);
    }

    private <T> T getEntity(Optional<T> optional, RuntimeException runtimeException) {
        return optional.orElseThrow(() -> runtimeException);
    }

    @Transactional
    public void removePost(Long postId) {
        Post entity = getEntity(searchPostId(postId),
                new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
        postRepository.delete(entity);
    }

    public ResponsePostPagingDto findPosts(RequestSearchPostDto requestSearchPostDto, Pageable pageable) {
        Page<ResponsePostListDto> responsePostListDtos = postRepository
                .getMembersIncludingLastCreate(
                        requestSearchPostDto.getAuthor(),
                        requestSearchPostDto.getTitle(),
                        requestSearchPostDto.getContent(),
                        pageable);

        return ResponsePostPagingDto.toMapper(responsePostListDtos);
    }
}
