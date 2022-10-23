package com.example.boardservice.module.hashtag.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "HASHTAGS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "HASH_TAG_NAMES", unique = true)
    private String hashTagName;

    @Builder
    public HashTag(String hashTagName) {
        this.hashTagName = hashTagName;
    }
}
