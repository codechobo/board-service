package com.example.boardservice.service;

import com.example.boardservice.domain.Member;
import com.example.boardservice.domain.Post;
import com.example.boardservice.domain.repository.MemberRepository;
import com.example.boardservice.domain.repository.PostRepository;
import com.example.boardservice.web.dto.post_dto.PostSaveRequestDto;
import com.example.boardservice.web.dto.post_dto.PostSaveResponseDto;
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
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

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
    @DisplayName("게시판 조회하다 -> pk로 조회")
    void findByPostId() {
        // given
        Post post = createPost();
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));

        // when
        PostSaveResponseDto postSaveResponseDto = postService.findByPostId(1L);

        // then
        assertThat(postSaveResponseDto.getAuthor()).isEqualTo(post.getAuthor());
        assertThat(postSaveResponseDto.getTitle()).isEqualTo(post.getTitle());
        assertThat(postSaveResponseDto.getContent()).isEqualTo(post.getContent());

        verify(postRepository).findById(anyLong());
    }

    private Post createPost() {
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