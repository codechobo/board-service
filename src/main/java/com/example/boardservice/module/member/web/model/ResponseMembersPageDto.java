package com.example.boardservice.module.member.web.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResponseMembersPageDto {
    private List<ResponseMemberListDto> elements;
    private int elementsSize;
    private int currentPage;
    private int totalPage;
    private int pageSize;

    private ResponseMembersPageDto(Page<ResponseMemberListDto> responseMemberListDtoPage) {
        this.elements = responseMemberListDtoPage.getContent();
        this.elementsSize = elements.size();
        this.currentPage = responseMemberListDtoPage.getNumber();
        this.totalPage = responseMemberListDtoPage.getTotalPages();
        this.pageSize = responseMemberListDtoPage.getSize();
    }

    public static ResponseMembersPageDto toMapper(Page<ResponseMemberListDto> responseMemberListDtoPage) {
        return new ResponseMembersPageDto(responseMemberListDtoPage);
    }
}
