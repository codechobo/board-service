package com.example.boardservice.service;

import com.example.boardservice.module.member.domain.Member;
import com.example.boardservice.module.member.domain.repository.MemberRepository;
import com.example.boardservice.module.post.domain.Post;
import com.example.boardservice.module.post.domain.repository.PostRepository;
import com.example.boardservice.module.post.service.PostService;
import com.example.boardservice.module.post.web.dto.PostSaveRequestDto;
import com.example.boardservice.module.post.web.dto.PostSaveResponseDto;
import com.example.boardservice.module.post.web.dto.PostUpdateRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostService postService;

    @Test
    @DisplayName("게시글 저장한다")
    void savePost() {
        // given
        Member member = createMember();
        Post post = createPost();

        PostSaveRequestDto postSaveRequestDto = PostSaveRequestDto.builder()
                .author(post.getAuthor())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        given(memberRepository.findByNickname(anyString())).willReturn(Optional.of(member));
        given(postRepository.save(any(Post.class))).willReturn(post);

        // when
        PostSaveResponseDto postSaveResponseDto = postService.savePost(postSaveRequestDto);

        // then
        assertThat(postSaveResponseDto.getAuthor()).isEqualTo(member.getNickname());
        assertThat(postSaveResponseDto.getTitle()).isEqualTo(post.getTitle());
        assertThat(postSaveResponseDto.getContent()).isEqualTo(post.getContent());

        verify(memberRepository).findByNickname(anyString());
        verify(postRepository).save(any(Post.class));
    }

    @Test
    @DisplayName("게시판 조회하다 -> title 로 조회")
    void findByPostId() {
        // given
        Post post = createPost();
        given(postRepository.findByTitle(anyString())).willReturn(Optional.of(post));

        // when
        PostSaveResponseDto postSaveResponseDto = postService.findByPostTitle(post.getTitle());

        // then
        assertThat(postSaveResponseDto.getAuthor()).isEqualTo(post.getAuthor());
        assertThat(postSaveResponseDto.getTitle()).isEqualTo(post.getTitle());
        assertThat(postSaveResponseDto.getContent()).isEqualTo(post.getContent());

        verify(postRepository).findByTitle(post.getTitle());
    }

    @Test
    @DisplayName("게시판 업데이트")
    void updateAfterFindPost() {
        // given
        Post post = createPost();
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));

        PostUpdateRequestDto postUpdateRequestDto = PostUpdateRequestDto.builder()
                .title("업데이트 된 제목")
                .content("업데이트 된 내용")
                .build();
        Long postIdBeforeUpdate = 1L;

        // when
        postService.updateAfterFindPost(postIdBeforeUpdate, postUpdateRequestDto);

        // then
        verify(postRepository).findById(anyLong());
    }

    @Test
    @DisplayName("게시글 삭제한다.")
    void removePost() {
        // given
        Post post = createPost();
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        willDoNothing().given(postRepository).delete(any(Post.class));

        // when
        postService.removePost(1L);

        // then
        verify(postRepository).findById(anyLong());
        verify(postRepository).delete(any(Post.class));
    }

    protected Post createPost() {
        Post post = Post.builder()
                .title("검정고무신 재밌지")
                .content("기영이 때문에 본다!")
                .build();
        post.addAuthor(createMember().getNickname());
        return post;
    }

    private Member createMember() {
        return Member.builder()
                .name("이기영")
                .nickname("까까머리")
                .password("test1234")
                .email("test@naver.com")
                .build();
    }

}