package com.example.boardservice.domain;


import com.example.boardservice.domain.base.TimeEntity;
import lombok.AllArgsConstructor;
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

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> child = new ArrayList<>(); // 댓글

    public Comment(String author) {
        this.author = author;
    }

    public void addComment(Comment comment) {
        this.parent = comment;
        this.child.add(comment);
    }

}
