package com.example.boardservice.module.post.domain.repository;

import com.example.boardservice.module.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    void deleteByAuthor(String author);
}
