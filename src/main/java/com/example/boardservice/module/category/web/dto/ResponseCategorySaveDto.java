package com.example.boardservice.module.category.web.dto;

import com.example.boardservice.module.category.domain.Category;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ResponseCategorySaveDto {

    private final String categoryName;

    private final List<String> categories;

    @Builder
    private ResponseCategorySaveDto(Category category) {
        this.categoryName = category.getCategoryName();
        this.categories = category.getChildCategories().stream()
                .map(Category::getCategoryName)
                .collect(Collectors.toList());;
    }

    public static ResponseCategorySaveDto of(Category category) {
        return ResponseCategorySaveDto.builder()
                .category(category)
                .build();
    }
}
