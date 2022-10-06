package com.example.boardservice.service;

import com.example.boardservice.module.comment.domain.Comment;
import com.example.boardservice.module.comment.domain.repository.CommentRepository;
import com.example.boardservice.module.comment.service.CommentService;
import com.example.boardservice.module.comment.web.comment_model.*;
import com.example.boardservice.module.member.domain.Member;
import com.example.boardservice.module.member.domain.repository.MemberRepository;
import com.example.boardservice.module.post.domain.Post;
import com.example.boardservice.module.post.domain.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
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

        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDto.builder()
                .author("까까머리")
                .content("테스트 제목")
                .build();

        Comment comment = Comment.builder()
                .author(member.getNickname())
                .content(commentSaveRequestDto.getContent())
                .build();
        comment.addPost(post);

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

        verify(memberRepository).findByNickname(commentSaveRequestDto.getAuthor());
        verify(postRepository).findById(findPostId);
        verify(commentRepository).save(comment);
    }

    @Test
    @DisplayName("대댓글 저장한다")
    void saveCommentOfComment() {
        // given
        Long postId = 1L;
        Long commentId = 1L;

        Member member = createMember();
        Post post = createPost();
        Comment comment = Comment.builder()
                .author(member.getNickname())
                .content("처음 댓글")
                .build();

        given(memberRepository.findByNickname(anyString())).willReturn(Optional.of(member));
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));

        CommentOfCommentRequestDto commentOfCommentRequestDto = CommentOfCommentRequestDto.builder()
                .author("까까머리")
                .content("대댓글1")
                .build();

        // when
        CommentOfCommentResponseDto result =
                commentService.saveCommentOfComment(postId, commentId, commentOfCommentRequestDto);

        // then
        assertAll(
                () -> assertThat(result.getContent()).isEqualTo(comment.getContent()),
                () -> assertThat(result.getComments().get(0).getContent())
                        .isEqualTo(commentOfCommentRequestDto.getContent())
        );

        verify(memberRepository).findByNickname(commentOfCommentRequestDto.getAuthor());
        verify(postRepository).findById(postId);
        verify(commentRepository).findById(commentId);
    }

    @Test
    @DisplayName("댓글 조회하다. -> pk 값으로 조회")
    void findCommentById() {
        // given
        Long commentId = 1L;

        Comment comment = Comment.builder().author("까까머리").content("처음댓글").build();
        given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));

        // when
        CommentSaveResponseDto result = commentService.findCommentById(commentId);

        // then
        assertThat(result.getAuthor()).isEqualTo(comment.getAuthor());
        assertThat(result.getContent()).isEqualTo(comment.getContent());

        verify(commentRepository).findById(commentId);
    }

    @Test
    @DisplayName("댓글 업데이트하다")
    void updateAfterFindComment() {
        // given
        Long commentId = 1L;

        Member member = createMember();
        Comment updateBeforeComment = createComment(member, "처음 댓글");

        given(commentRepository.findById(anyLong())).willReturn(Optional.of(updateBeforeComment));

        CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDto.builder()
                .content("테스트 글 수정")
                .build();

        // when
        commentService.updateAfterFindComment(commentId, commentUpdateRequestDto);

        // then
        assertThat(updateBeforeComment.getContent()).isEqualTo(commentUpdateRequestDto.getContent());

        verify(commentRepository).findById(commentId);
    }

    @Test
    @DisplayName("댓글 삭제하기")
    void removeComment() {
        // given
        Long commentId = 1L;
        Member member = createMember();
        Comment comment = createComment(member, "처음댓글");

        given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));
        willDoNothing().given(commentRepository).delete(comment);

        // when
        commentService.removeComment(commentId);

        // then
        verify(commentRepository).delete(comment);
    }

    @Test
    @DisplayName("모든 댓글 조회")
    void findComments() {
        // given
        Member member = createMember();
        Comment comment = createComment(member, "ㅋㅋㅋㅋㅋㅋ");
        List<Comment> comments = new ArrayList<>() {{
            add(comment);
            add(comment);
            add(comment);
        }};

        given(commentRepository.findAll()).willReturn(comments);

        // when
        List<CommentSaveResponseDto> result = commentService.findComments();

        // then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(3),
                () -> assertThat(result.isEmpty()).isFalse(),
                () -> assertThat(result.stream().allMatch(
                        commentSaveResponseDto -> commentSaveResponseDto
                                .getContent().equals(comment.getContent()))).isTrue()
        );
    }

    private Comment createComment(Member member, String content) {
        return Comment.builder()
                .author(member.getNickname())
                .content(content)
                .build();
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