package com.example.boardservice.module.post.domain;

import com.example.boardservice.module.base.TimeEntity;
import lombok.*;

import javax.persistence.*;

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

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addAuthor(String nickName) {
        this.author = nickName;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
