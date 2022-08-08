package com.example.boardservice.domain.repository;

import com.example.boardservice.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    void deleteByAuthor(String author);
}
