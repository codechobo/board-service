package com.example.boardservice.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    @Test
    void CommentTest() {
        Member member = Member.builder()
                .name("이기영")
                .nickname("까까머리")
                .email("기영@naver.com")
                .password("test1234")
                .build();

        Post post = Post.builder()
                .title("검정고무신")
                .content("재미지지")
                .build();
        post.addAuthor(member.getNickname());


        Member member1 = Member.builder()
                .name("이기철")
                .nickname("기영이 형")
                .email("기철@naver.com")
                .password("test1234")
                .build();

        Comment comment = Comment.builder()
                .author(member1.getNickname())
                .content("기영이 바보")
                .build();
        comment.addPost(post);

        Post post1 = comment.getPost();
        System.out.println(post1.getAuthor());

        Comment comment1 = Comment.builder()
                .author(member1.getNickname())
                .content("기영이 바보@@")
                .build();
        comment.addComment(post, comment1);

        Comment comment2 = Comment.builder()
                .author(member1.getNickname())
                .content("기영이 바보@@@@@")
                .build();
        comment.addComment(post, comment2);

        System.out.println(comment);

    }

}