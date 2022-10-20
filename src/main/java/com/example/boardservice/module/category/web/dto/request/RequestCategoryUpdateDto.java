package com.example.boardservice.module.category.web.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RequestCategoryUpdateDto {

    private String categoryName;

    @Builder
    private RequestCategoryUpdateDto(String categoryName) {
        this.categoryName = categoryName;
    }
}
