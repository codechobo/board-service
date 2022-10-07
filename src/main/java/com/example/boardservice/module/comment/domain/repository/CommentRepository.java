package com.example.boardservice.module.comment.domain.repository;

import com.example.boardservice.module.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository{
}
