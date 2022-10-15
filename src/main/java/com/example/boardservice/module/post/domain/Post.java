package com.example.boardservice.module.post.domain;

import com.example.boardservice.module.base.TimeEntity;
import com.example.boardservice.module.category.domain.Category;
import com.example.boardservice.module.comment.domain.Comment;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(of = "id")
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

    private boolean published = false; // 공개 여부

    private LocalDateTime publishDateTime; // 게시글 공개 된 시간

    private LocalDateTime closeDateTime; // 게시글 비공개 된 시간

    private int viewCount; // 게시글 조회수

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();

    @Builder
    public Post(String author, String title, String content, boolean published) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.published = published;
    }

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void publish() {
        if (!this.published) {
            this.published = true;
            this.publishDateTime = LocalDateTime.now();
        } else {
            throw new IllegalArgumentException("해당 게시글은 이미 공개 한 상태입니다.");
        }
    }

    public void close() {
        if (this.published) {
            this.published = false;
        } else {
            throw new IllegalArgumentException("해당 게시글은 이미 비공개 한 상태입니다.");
        }
    }

    public void checkPublished() {
        if (!this.isPublished()) {
            throw new IllegalArgumentException("해당 게시글은 비공개 된 상태입니다.");
        }
    }

    public void updateViewCount() {
        int currentCount = getViewCount();
        currentCount += 1;
        this.viewCount = currentCount;
    }
}
