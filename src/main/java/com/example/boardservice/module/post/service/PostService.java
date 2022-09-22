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

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostSaveResponseDto savePost(PostSaveRequestDto postSaveRequestDto) {
        Member member = getMemberEntity(postSaveRequestDto);

        Post post = postSaveRequestDto.toEntity();
        post.addAuthor(member.getNickname());

        Post savedPost = postRepository.save(post);
        return PostSaveResponseDto.builder()
                .post(savedPost)
                .build();
    }

    private Member getMemberEntity(PostSaveRequestDto postSaveRequestDto) {
        return memberRepository.findByNickname(postSaveRequestDto.getAuthor())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }

    public PostSaveResponseDto findByPostId(Long postId) {
        Post post = getPostEntity(postId);
        return PostSaveResponseDto.builder()
                .post(post)
                .build();
    }

    @Transactional
    public void updateAfterFindPost(Long postId, PostUpdateRequestDto postUpdateRequestDto) {
        Post post = getPostEntity(postId);
        post.updateTitle(postUpdateRequestDto.getTitle());
        post.updateContent(postUpdateRequestDto.getContent());
    }

    @Transactional
    public void removePost(Long postId) {
        Post post = getPostEntity(postId);
        postRepository.delete(post);
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

    private Post getPostEntity(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }
}
