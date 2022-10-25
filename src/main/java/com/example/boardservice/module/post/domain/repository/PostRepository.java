package com.example.boardservice.module.post.domain.repository;

import com.example.boardservice.module.post.domain.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository {
    Optional<Post> findByTitle(String title);

    @EntityGraph(value = "postWithHashTag", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Post> findPostsWithHashTagsById(Long postsId);
}
