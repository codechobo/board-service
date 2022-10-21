package com.example.boardservice.module.category.web.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class RequestCategoryUpdateDto {

    @NotEmpty
    private String categoryName;

    @Builder
    public RequestCategoryUpdateDto(String categoryName) {
        this.categoryName = categoryName;
    }
}
