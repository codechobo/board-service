package com.example.boardservice.service;

import com.example.boardservice.domain.Comment;
import com.example.boardservice.domain.Member;
import com.example.boardservice.domain.Post;
import com.example.boardservice.domain.repository.MemberRepository;
import com.example.boardservice.domain.repository.PostRepository;
import com.example.boardservice.web.dto.comment_dto.CommentSaveRequestDto;
import com.example.boardservice.web.dto.comment_dto.CommentSaveResponseDto;
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
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @Mock
    PostRepository postRepository;

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    CommentService commentService;


    @Test
    @DisplayName("댓글 저장한다")
    void saveComment() {
        // given
        Member member = createMember();

        Post post = createPost();
        post.addAuthor(member.getNickname());

        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDto.builder()
                .author("까까머리")
                .content("테스트 제목")
                .build();

        Comment comment = Comment.builder()
                .author(member.getNickname())
                .content(commentSaveRequestDto.getContent())
                .build();

        given(memberRepository.findByNickname(anyString())).willReturn(Optional.of(member));
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        given(commentRepository.save(any(Comment.class))).willReturn(comment);
        Long findPostId = 1L;

        // when
        CommentSaveResponseDto commentSaveResponseDto = commentService
                .saveComment(findPostId, commentSaveRequestDto);

        // then
        assertThat(commentSaveResponseDto.getContent()).isEqualTo(commentSaveRequestDto.getContent());
        assertThat(commentSaveResponseDto.getAuthor()).isEqualTo(commentSaveRequestDto.getAuthor());

        verify(memberRepository).findByNickname(anyString());
        verify(postRepository).findById(anyLong());
        verify(commentRepository).save(any(Comment.class));
    }

    private Post createPost() {
        return Post.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .build();
    }

    private Member createMember() {
        return Member.builder()
                .name("이기영")
                .nickname("까까머리")
                .email("test@naver.com")
                .password("test1234")
                .build();
    }
}