package com.example.boardservice.domain;


import com.example.boardservice.domain.base.TimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "POSTS_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PARENT_ID")
    private Comment parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Comment> child = new ArrayList<>();

    @Builder
    public Comment(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public void addPost(Post post) {
        this.post = post;
    }

    public void addComment(Comment comment) {
        this.parent = comment;
        this.child.add(comment);
    }
}
