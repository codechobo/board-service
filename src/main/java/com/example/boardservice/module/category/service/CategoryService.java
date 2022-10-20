package com.example.boardservice.module.category.service;

import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.module.category.domain.Category;
import com.example.boardservice.module.category.repository.CategoryRepository;
import com.example.boardservice.module.category.web.dto.response.ResponseCategorySaveDto;
import com.example.boardservice.module.post.domain.repository.PostRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    @Transactional
    public ResponseCategorySaveDto saveCategory(String requestCategoryName) {
        duplicatedCategoryNameChecked(requestCategoryName);

        Category category = Category.builder()
                .categoryName(requestCategoryName)
                .build();

        Category savedCategory = categoryRepository.save(category);
        return ResponseCategorySaveDto.of(savedCategory);
    }

    private void duplicatedCategoryNameChecked(String requestCategoryName) {
        if (isExists(requestCategoryName)) {
            throw new DuplicateRequestException(ErrorCode.REQUEST_DATA_DUPLICATED.getMessage());
        }
    }

    private boolean isExists(String requestCategoryName) {
        return categoryRepository.existsByCategoryName(requestCategoryName);
    }

    @Transactional
    public ResponseCategorySaveDto saveCategoryOfCategory(Long categoryId, String requestCategoryName) {
        duplicatedCategoryNameChecked(requestCategoryName);
        Category parentCategory = getCategory(categoryId);

        Category childCategory = Category.builder()
                .categoryName(requestCategoryName)
                .build();

        parentCategory.addChildCategories(childCategory);

        Category savedChildCategory = categoryRepository.save(childCategory);
        return ResponseCategorySaveDto.of(savedChildCategory);
    }

    public ResponseCategorySaveDto findCategoryById(Long categoryId) {
        Category category = getCategory(categoryId);
        return ResponseCategorySaveDto.of(category);
    }

    private Category getCategory(Long categoryId) {
        return categoryRepository.findCategoryWithParentAndPostsById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }
}
