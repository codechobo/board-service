package com.example.boardservice.module.hashtag.domain.repository;

import com.example.boardservice.module.hashtag.domain.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface HashTagRepository extends JpaRepository<HashTag, Long> {

    Optional<HashTag> findByHashTagName(String hashTagName);
    boolean existsByHashTagName(String hashTagName);
    List<HashTag> findAllByOrderByHashTagNameDesc();
}
