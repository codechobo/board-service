package com.example.boardservice.module.category.web.dto;

import com.example.boardservice.module.category.domain.Category;
import com.example.boardservice.module.post.web.dto.response.ResponsePostSaveDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ResponseCategorySaveDto {

    private final LocalDateTime createAt; // 생성시간
    private final String categoryName; // 카테고리이름
    private final List<ResponsePostSaveDto> responsePostSaveDtoList; // 게시글 리스트
    // 카테고리 안에 카테고리들을 출력
    private final List<ResponseCategorySaveDto> responseCategorySaveDtoList;

    @Builder
    private ResponseCategorySaveDto(Category category) {
        this.createAt = category.getCreatedAt();
        this.responsePostSaveDtoList = category.getPosts().stream()
                .map(ResponsePostSaveDto::of)
                .sorted(Comparator.comparing(ResponsePostSaveDto::getTitle))
                .collect(Collectors.toList());
        this.categoryName = category.getCategoryName();
        this.responseCategorySaveDtoList = category.getChildCategories().stream()
                .map(ResponseCategorySaveDto::of)
                .sorted(Comparator.comparing(ResponseCategorySaveDto::getCategoryName))
                .collect(Collectors.toList());
    }

    public static ResponseCategorySaveDto of(Category category) {
        return ResponseCategorySaveDto.builder()
                .category(category)
                .build();
    }
}
