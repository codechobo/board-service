package com.example.boardservice.module.post.service;

import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.factory.RequestDtoFactory;
import com.example.boardservice.module.member.domain.Member;
import com.example.boardservice.module.member.domain.repository.MemberRepository;
import com.example.boardservice.module.member.web.dto.request.RequestMemberSaveDto;
import com.example.boardservice.module.post.domain.Post;
import com.example.boardservice.module.post.domain.repository.PostRepository;
import com.example.boardservice.module.post.web.dto.request.RequestPostSaveDto;
import com.example.boardservice.module.post.web.dto.request.RequestPostUpdateDto;
import com.example.boardservice.module.post.web.dto.request.RequestSearchPostDto;
import com.example.boardservice.module.post.web.dto.response.ResponsePostPagingDto;
import com.example.boardservice.module.post.web.dto.response.ResponsePostSaveDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    public void removeAll() {
        memberRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 생성 테스트 성공")
    void savePost_success() {
        // given
        RequestMemberSaveDto requestMemberSaveDto = RequestDtoFactory.createMemberSaveRequestDto();
        Member member = memberRepository.save(requestMemberSaveDto.toEntity());

        RequestPostSaveDto requestDto = RequestDtoFactory.createPostSaveRequestDto(member.getNickname());

        // when
        ResponsePostSaveDto responseDto = postService.savePost(requestDto);

        // then
        assertEquals(requestDto.getAuthor(), responseDto.getAuthor());
        assertEquals(requestDto.getTitle(), responseDto.getTitle());
        assertEquals(requestDto.getContent(), responseDto.getContent());
    }

    @Test
    @DisplayName("게시글 생성 테스트 실패 -> 회원 닉네임이 존재하지 않음")
    void savePost_fail() {
        // given
        RequestMemberSaveDto requestMemberSaveDto = RequestDtoFactory.createMemberSaveRequestDto();
        memberRepository.save(requestMemberSaveDto.toEntity());

        RequestPostSaveDto requestDto = RequestDtoFactory.createPostSaveRequestDto("다른 닉네임");

        // when && then
        assertThatThrownBy(() -> postService.savePost(requestDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(ErrorCode.NOT_FOUND_ENTITY.getMessage());
    }

    @Test
    @DisplayName("게시글 조회 테스트")
    void findByPostId() {
        // given
        RequestMemberSaveDto requestMemberSaveDto = RequestDtoFactory.createMemberSaveRequestDto();
        Member member = memberRepository.save(requestMemberSaveDto.toEntity());

        RequestPostSaveDto requestPostSaveDto = RequestDtoFactory.createPostSaveRequestDto(member.getNickname());
        Post savedPost = postRepository.save(requestPostSaveDto.toEntity());

        // when
        ResponsePostSaveDto result = postService.findByPostId(savedPost.getId());

        // then
        assertEquals(requestPostSaveDto.getAuthor(), result.getAuthor());
        assertEquals(requestPostSaveDto.getTitle(), result.getTitle());
        assertEquals(requestPostSaveDto.getContent(), result.getContent());
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void updateAfterFindPost() {
        // given
        RequestMemberSaveDto requestMemberSaveDto = RequestDtoFactory.createMemberSaveRequestDto();
        Member member = memberRepository.save(requestMemberSaveDto.toEntity());

        RequestPostSaveDto requestPostSaveDto = RequestDtoFactory.createPostSaveRequestDto(member.getNickname());
        Post beforePost = postRepository.save(requestPostSaveDto.toEntity());

        RequestPostUpdateDto requestPostUpdateDto = RequestDtoFactory.createPostUpdateRequestDto();

        // when
        postService.updateAfterFindPost(beforePost.getId(), requestPostUpdateDto);

        // then
        Post afterPost = postRepository.findById(beforePost.getId()).get();
        assertNotEquals(beforePost.getTitle(), afterPost.getTitle());
        assertNotEquals(beforePost.getContent(), afterPost.getContent());
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void removePost() {
        // given
        RequestMemberSaveDto requestMemberSaveDto = RequestDtoFactory.createMemberSaveRequestDto();
        Member member = memberRepository.save(requestMemberSaveDto.toEntity());

        RequestPostSaveDto requestPostSaveDto = RequestDtoFactory.createPostSaveRequestDto(member.getNickname());
        Post post = postRepository.save(requestPostSaveDto.toEntity());

        // when
        postService.removePost(post.getId());

        // then
        assertThatThrownBy(() -> postRepository.findById(post.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage())))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(ErrorCode.NOT_FOUND_ENTITY.getMessage());
    }

    @Test
    @DisplayName("작성자 또는 글 제목 또는 글 내용으로 게시글 조회하여 페이징 처리 테스트")
    void findPosts() {
        // given
        RequestMemberSaveDto requestMemberSaveDto = RequestDtoFactory.createMemberSaveRequestDto();
        Member member = memberRepository.save(requestMemberSaveDto.toEntity());

        RequestPostSaveDto requestPostSaveDto = RequestDtoFactory.createPostSaveRequestDto(member.getNickname());
        List<RequestPostSaveDto> requestPostSaveList = Arrays.asList(requestPostSaveDto, requestPostSaveDto, requestPostSaveDto, requestPostSaveDto, requestPostSaveDto);
        List<Post> posts = requestPostSaveList.stream().map(RequestPostSaveDto::toEntity).collect(Collectors.toList());

        postRepository.saveAll(posts);

        // when
        RequestSearchPostDto requestSearchPostDto = RequestDtoFactory.createRequestSearchPostDto();
        ResponsePostPagingDto result = postService.findPosts(requestSearchPostDto, Pageable.ofSize(5));

        // then
        assertThat(result.getCurrentPage()).isEqualTo(0);
        assertThat(result.getPageSize()).isEqualTo(5);
        assertThat(result.getTotalPage()).isEqualTo(1);
        assertThat(result.getElementsSize()).isEqualTo(posts.size());
        assertThat(result.getElements().size()).isEqualTo(posts.size());
        assertThat(result.getElements().isEmpty()).isFalse();
    }
}