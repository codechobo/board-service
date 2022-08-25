package com.example.boardservice.module.post.domain.repository;

import com.example.boardservice.module.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository {
    Optional<Post> findByTitle(String title);
}
