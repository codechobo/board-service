package com.example.boardservice.module.category.web.dto;

import com.example.boardservice.module.category.domain.Category;
import com.example.boardservice.module.post.web.dto.response.ResponsePostSaveDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ResponseCategorySaveDto {

    private final LocalDateTime createAt; // 생성시간
    private final String categoryName; // 카테고리이름
    private final List<ResponsePostSaveDto> responsePostSaveDtoLis; // 게시글 리스트
    // 카테고리 안에 카테고리들을 출력
    private final List<ResponseCategorySaveDto> responseCategorySaveDtoList;

    @Builder
    public ResponseCategorySaveDto(LocalDateTime createAt, String categoryName, List<ResponsePostSaveDto> responsePostSaveDtoLis, List<ResponseCategorySaveDto> responseCategorySaveDtoList) {
        this.createAt = createAt;
        this.categoryName = categoryName;
        this.responsePostSaveDtoLis = responsePostSaveDtoLis;
        this.responseCategorySaveDtoList = responseCategorySaveDtoList;
    }

    public static ResponseCategorySaveDto of(Category category) {
        return ResponseCategorySaveDto.builder()
                .createAt(category.getCreatedAt())
                .categoryName(category.getCategoryName())
                .responsePostSaveDtoLis(category.getPosts().stream()
                        .map(ResponsePostSaveDto::of).collect(Collectors.toList()))
                .responseCategorySaveDtoList(category.getChildCategories().stream()
                        .map(ResponseCategorySaveDto::of).collect(Collectors.toList()))
                .build();
    }

}
