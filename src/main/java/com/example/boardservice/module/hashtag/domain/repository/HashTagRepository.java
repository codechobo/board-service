package com.example.boardservice.module.hashtag.domain.repository;

import com.example.boardservice.module.hashtag.domain.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface HashTagRepository extends JpaRepository<HashTag, Long> {
    boolean existsByHashTagName(String hashTagName);
    List<HashTag> findAllByOrderByHashTagNameDesc();
}
