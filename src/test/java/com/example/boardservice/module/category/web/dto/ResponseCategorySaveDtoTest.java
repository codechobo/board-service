package com.example.boardservice.module.category.web.dto;

import com.example.boardservice.module.category.domain.Category;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ResponseCategorySaveDtoTest {

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("ResponseCategorySaveDto 테스트 순환참조 이슈")
    void ResponseCategorySaveDtoTest() throws JsonProcessingException {
        Category category = Category.builder()
                .categoryName("spring")
                .build();

        Category categoryOfCategory1 = Category.builder()
                .categoryName("boot")
                .build();

        Category categoryOfCategory2 = Category.builder()
                .categoryName("java")
                .build();

        category.addChildCategories(categoryOfCategory1);
        categoryOfCategory1.addChildCategories(categoryOfCategory2);





    }
}