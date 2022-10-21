package com.example.boardservice.module.post.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResponsePostPagingDto {

    private List<ResponsePostListDto> elements;

    private int elementsSize;

    private int currentPage;

    private int totalPage;

    private int pageSize;

    @Builder
    private ResponsePostPagingDto(Page<ResponsePostListDto> responsePostListDtos) {
        this.elements = responsePostListDtos.getContent();
        this.elementsSize = elements.size();
        this.currentPage = responsePostListDtos.getNumber();
        this.totalPage = responsePostListDtos.getTotalPages();
        this.pageSize = responsePostListDtos.getSize();
    }

    public static ResponsePostPagingDto toMapper(Page<ResponsePostListDto> responsePostListDtos) {
        return new ResponsePostPagingDto(responsePostListDtos);
    }
}
