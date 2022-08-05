package com.example.boardservice.domain;

import com.example.boardservice.domain.base.TimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "AUTHOR", length = 30)
    private String author; // 글쓴이

    @Column(name = "TITLE", length = 100)
    private String title; // 제목

    @Column(name = "CONTENT")
    @Lob
    private String content; // 글 내용

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addAuthor(String nickName) {
        this.author = nickName;
    }
}
