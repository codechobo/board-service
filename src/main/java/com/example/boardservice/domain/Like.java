package com.example.boardservice.domain;

import com.example.boardservice.domain.base.TimeEntity;
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
    @JoinTable(name = "MEMBERS", joinColumns = {
            @JoinColumn(name = "MEMBERS_ID")})
    private Long membersId;

    @Column(name = "POSTS_ID")
    @JoinTable(name = "POSTS", joinColumns = {
            @JoinColumn(name = "POSTS_ID")})
    private Long postsId;

    @Builder
    public Like(Long postsId, Long membersId) {
        this.membersId = membersId;
        this.postsId = postsId;
    }
}
