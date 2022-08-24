package com.example.boardservice.module.comment.domain;


import com.example.boardservice.module.base.TimeEntity;
import com.example.boardservice.module.post.domain.Post;
import lombok.*;

import javax.persistence.*;
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

    @Builder
    public Comment(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public void addPost(Post post) {
        this.post = post;
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
}
