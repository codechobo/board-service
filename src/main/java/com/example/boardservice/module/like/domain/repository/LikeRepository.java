package com.example.boardservice.module.like.domain.repository;

import com.example.boardservice.module.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByMembersIdAndPostsId(Long memberId, Long postId);
    Optional<Like> findByMembersIdAndCommentsId(Long memberId, Long commentsId);
    long countByPostsId(Long postsId);
    boolean existsByPostsId(Long postId);

    boolean existsByCommentsId(Long commentsId);
}
