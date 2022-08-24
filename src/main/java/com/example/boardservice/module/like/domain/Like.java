package com.example.boardservice.module.like.domain;

import com.example.boardservice.module.base.TimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "LIKES")
public class Like extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIKES_ID")
    private Long id;

    @Column(name = "MEMBERS_ID")
    private Long membersId;

    @Column(name = "POSTS_ID")
    private Long postsId;

    @Column(name = "COMMENTS_ID")
    private Long commentsId;

    @Builder
    public Like(Long postsId, Long membersId, Long commentsId) {
        this.membersId = membersId;
        this.postsId = postsId;
        this.commentsId = commentsId;
    }
}
