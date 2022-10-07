package com.example.boardservice.module.post.domain;

import com.example.boardservice.module.comment.domain.Comment;
import com.example.boardservice.module.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostTest {

    @Test
    @DisplayName("게시글 생성하고 댓글 달기 테스트")
    void PostTest() {
        Member member = Member.builder()
                .name("이기영")
                .nickname("까까머리")
                .email("test123@naver.com")
                .password("test1234")
                .build();

        Post post = Post.builder()
                .author(member.getNickname())
                .title("글 제목입니다.")
                .content("글 내용입니다.")
                .build();

        Comment comment = Comment.builder()
                .author(member.getNickname())
                .content("댓글 입니다.")
                .build();

        Comment commentOfComment = Comment.builder()
                .author(member.getNickname())
                .content("대댓글 입니다.")
                .build();

        commentOfComment.addPost(post);
        comment.addComment(post, commentOfComment);
        System.out.println(post);

    }

}