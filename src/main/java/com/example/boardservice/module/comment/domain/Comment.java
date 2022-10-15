package com.example.boardservice.module.comment.domain;


import com.example.boardservice.module.base.TimeEntity;
import com.example.boardservice.module.post.domain.Post;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(of = "id")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COMMENTS")
@Entity
public class Comment extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENTS_ID")
    private Long id;

    @Column(name = "AUTHOR", length = 30)
    private String author; // 작성자

    @Column(name = "CONTENT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POSTS_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Comment parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Comment> child = new ArrayList<>();

    @Column(name = "PUBLISHED")
    private boolean published;

    @Column(name = "PUBLISH_DATE_TIME")
    private LocalDateTime publishDateTime;

    @Builder
    public Comment(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public void addPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    public void addComment(Post post, Comment comment) {
        addPost(post);
        this.child.add(comment);
        comment.addParent(this);
    }

    public void addParent(Comment comment) {
        this.parent = comment;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void publish() {
        if (!this.published) {
            this.published = true;
            this.publishDateTime = LocalDateTime.now();
        } else {
            throw new IllegalArgumentException("해당 댓글은 이미 공개 한 상태입니다.");
        }
    }

    public void close() {
        if (this.published) {
            this.published = false;
        } else {
            throw new IllegalArgumentException("해당 댓글은 이미 비공개 한 상태입니다.");
        }
    }

    public void checkPublished() {
        if (!this.isPublished()) {
            throw new IllegalArgumentException("해당 댓글은 비공개 된 상태입니다.");
        }
    }
}
