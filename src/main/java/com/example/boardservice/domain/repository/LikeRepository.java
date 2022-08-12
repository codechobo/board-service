package com.example.boardservice.domain.repository;

import com.example.boardservice.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
