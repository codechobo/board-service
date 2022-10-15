package com.example.boardservice.module.category.web;

import com.example.boardservice.module.category.domain.Category;
import com.example.boardservice.module.category.repository.CategoryRepository;
import com.example.boardservice.module.category.service.CategoryService;
import com.example.boardservice.module.category.web.dto.ResponseCategorySaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    // TODO 게시글에도 카테고리 추가 기능이 되야함
    //  연관관계 생각

    @PostMapping("/categories")
    public ResponseEntity<ResponseCategorySaveDto> createCategory(@Valid @NotBlank @RequestParam(name = "category_name") String requestCategoryName) {
        ResponseCategorySaveDto responseCategorySaveDto = categoryService.saveCategory(requestCategoryName);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseCategorySaveDto);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<ResponseCategorySaveDto> createCategoryOfCategory(@Valid @NotNull @PathVariable("id") Long categoryId,
                                                                            @Valid @NotBlank @RequestParam(name = "category_name") String requestCategoryName) {
        ResponseCategorySaveDto responseCategorySaveDto = categoryService.saveCategoryOfCategory(categoryId, requestCategoryName);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseCategorySaveDto);
    }

    @GetMapping("/categories")
    public ResponseEntity<ResponseCategorySaveDto> getCategories() {
        Category category = categoryRepository.findById(1L).orElseThrow();
        return ResponseEntity.ok(ResponseCategorySaveDto.of(category));
    }


}
