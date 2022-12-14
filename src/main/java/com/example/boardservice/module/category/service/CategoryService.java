package com.example.boardservice.module.category.service;

import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.module.category.domain.Category;
import com.example.boardservice.module.category.repository.CategoryRepository;
import com.example.boardservice.module.category.web.dto.request.RequestCategoryUpdateDto;
import com.example.boardservice.module.category.web.dto.response.ResponseCategorySaveDto;
import com.example.boardservice.module.category.web.dto.response.ResponseCategoryUpdateDto;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

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

    @Transactional
    public ResponseCategoryUpdateDto updateCategory(Long categoryId, RequestCategoryUpdateDto requestCategoryUpdateDto) {
        Category category = getCategory(categoryId);
        category.updateCategoryName(requestCategoryUpdateDto.getCategoryName());
        return ResponseCategoryUpdateDto.of(category);
    }

    public Category getCategory(Long categoryId) {
        return categoryRepository.findCategoryWithParentAndPostsById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = getCategory(categoryId);
        categoryRepository.delete(category);
    }
}
