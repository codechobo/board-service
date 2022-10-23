package com.example.boardservice.module.hashtag.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "HASH_TAGS")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HASH_TAGS_ID")
    private Long id;

    @Column(nullable = false)
    private String hashTagName;

    @Builder
    public HashTag(String hashTagName) {
        this.hashTagName = hashTagName;
    }
}
