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

    @PostMapping("/categories")
    public ResponseEntity<ResponseCategorySaveDto> createCategory(@Valid @NotBlank @RequestParam(name = "category_name") String requestCategoryName) {
        ResponseCategorySaveDto responseCategorySaveDto = categoryService.saveCategory(requestCategoryName);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseCategorySaveDto);
    }

    @PostMapping("/categories/{id}")
    public ResponseEntity<ResponseCategorySaveDto> createCategoryOfCategory(@Valid @NotNull @PathVariable("id") Long categoryId,
                                                                            @Valid @NotBlank @RequestParam(name = "category_name") String requestCategoryName) {
        ResponseCategorySaveDto responseCategorySaveDto = categoryService.saveCategoryOfCategory(categoryId, requestCategoryName);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseCategorySaveDto);
    }

    @GetMapping("/categories")
    public ResponseEntity<ResponseCategorySaveDto> getList() {
        Category category = categoryRepository.findById(1L).orElseThrow();
        return ResponseEntity.ok(ResponseCategorySaveDto.of(category));
    }

}
