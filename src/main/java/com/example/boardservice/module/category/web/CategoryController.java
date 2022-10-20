package com.example.boardservice.module.category.web;

import com.example.boardservice.module.category.service.CategoryService;
import com.example.boardservice.module.category.web.dto.request.RequestCategoryUpdateDto;
import com.example.boardservice.module.category.web.dto.response.ResponseCategoryUpdateDto;
import com.example.boardservice.module.category.web.dto.response.ResponseCategorySaveDto;
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

    @GetMapping("/categories/{id}")
    public ResponseEntity<ResponseCategorySaveDto> getCategories(@PathVariable("id") Long categoryId) {
        ResponseCategorySaveDto responseCategorySaveDto = categoryService.findCategoryById(categoryId);
        return ResponseEntity.ok(responseCategorySaveDto);
    }

    @PutMapping("/categoreis/{id}")
    public ResponseEntity<ResponseCategoryUpdateDto> updateCategory(@PathVariable("id") Long categoryId,
                                                                    @Valid @RequestBody RequestCategoryUpdateDto requestCategoryUpdateDto) {
        ResponseCategoryUpdateDto responseCategoryUpdateDto = categoryService.updateCategory(categoryId, requestCategoryUpdateDto);
        return ResponseEntity.ok(responseCategoryUpdateDto);
    }

    @DeleteMapping("/categoreis/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }
}
