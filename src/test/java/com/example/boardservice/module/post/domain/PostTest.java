package com.example.boardservice.module.post.domain;

import com.example.boardservice.module.category.domain.Category;
import com.example.boardservice.module.category.repository.CategoryRepository;
import com.example.boardservice.module.comment.domain.Comment;
import com.example.boardservice.module.comment.domain.repository.CommentRepository;
import com.example.boardservice.module.member.domain.Member;
import com.example.boardservice.module.member.domain.repository.MemberRepository;
import com.example.boardservice.module.post.domain.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
class PostTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CommentRepository commentRepository;

    @Rollback(value = false)
    @Test
    @DisplayName("게시글 생성하고 댓글 달기 테스트")
    void PostTest() {
        Member member = Member.builder()
                .name("이기영")
                .nickname("까까머리")
                .email("test123@naver.com")
                .password("test1234")
                .build();
        memberRepository.save(member);

        Post post = Post.builder()
                .author(member.getNickname())
                .title("글 제목입니다.")
                .content("글 내용입니다.")
                .build();
        postRepository.save(post);


        Category category = Category.builder().categoryName("Spring").build();
        Category category2 = Category.builder().categoryName("Boot").build();
        category.addChildCategories(category2);
        categoryRepository.save(category);
        categoryRepository.save(category2);



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

        commentRepository.save(comment);
        commentRepository.save(commentOfComment);

    }

}