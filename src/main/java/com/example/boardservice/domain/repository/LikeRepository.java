package com.example.boardservice.domain.repository;

import com.example.boardservice.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByMembersIdAndPostsId(Long memberId, Long postId);
    Optional<Like> findByMembersIdAndCommentsId(Long memberId, Long commentId);
}
