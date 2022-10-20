package com.example.boardservice.module.category.repository;

import com.example.boardservice.module.category.domain.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryName(String categoryName);

    @EntityGraph(value = "category-entity-graph-with-post", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Category> findCategoryWithParentAndPostsById(Long categoryId);
}
