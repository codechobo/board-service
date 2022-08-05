package com.example.boardservice.domain;


import com.example.boardservice.domain.base.TimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;

import javax.persistence.*;
import javax.validation.Valid;
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
    @JoinColumn(name = "POSTS_ID")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> child = new ArrayList<>();

    @Builder
    public Comment(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public void addComment(Post post, Comment comment) {
        if (post == null || comment == null) {
            throw new EntityNotFoundException("엔티티를 찾을 수 없습니다.");
        }

        this.post = post;
        this.parent = comment;
        this.child.add(comment);
    }

}
