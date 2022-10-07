package com.example.boardservice.module.post.domain;

import com.example.boardservice.module.base.TimeEntity;
import com.example.boardservice.module.comment.domain.Comment;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "POSTS")
@Entity
public class Post extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POSTS_ID")
    private Long id;

    @Column(name = "AUTHOR", length = 30)
    private String author; // 글쓴이

    @Column(name = "TITLE", length = 100)
    private String title; // 제목

    @Lob
    @Column(name = "CONTENT")
    private String content; // 글 내용

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Post(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.addPost(this);
    }
}
