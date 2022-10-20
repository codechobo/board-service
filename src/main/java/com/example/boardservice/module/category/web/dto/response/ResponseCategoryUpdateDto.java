package com.example.boardservice.module.category.web.dto.response;

import com.example.boardservice.module.category.domain.Category;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseCategoryUpdateDto {

    private final LocalDateTime updateAt;
    private final String categoryName;

    @Builder
    private ResponseCategoryUpdateDto(LocalDateTime updateAt, String categoryName) {
        this.updateAt = updateAt;
        this.categoryName = categoryName;
    }

    public static ResponseCategoryUpdateDto of(Category category) {
        return ResponseCategoryUpdateDto.builder()
                .updateAt(category.getUpdatedAt())
                .categoryName(category.getCategoryName())
                .build();
    }
}
