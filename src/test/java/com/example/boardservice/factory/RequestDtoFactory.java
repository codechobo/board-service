package com.example.boardservice.factory;

import com.example.boardservice.module.member.web.dto.request.RequestMemberSaveDto;
import com.example.boardservice.module.post.web.dto.request.RequestPostSaveDto;
import com.example.boardservice.module.post.web.dto.request.RequestPostUpdateDto;
import com.example.boardservice.module.post.web.dto.request.RequestSearchPostDto;

public class RequestDtoFactory {
    public static RequestMemberSaveDto createMemberSaveRequestDto() {
        return RequestMemberSaveDto.builder()
                .name("이기영")
                .nickname("까까머리")
                .email("까까머리@naver.com")
                .password("test1234")
                .build();
    }

    public static RequestPostSaveDto createPostSaveRequestDto(String nickname) {
        return RequestPostSaveDto.builder()
                .author(nickname)
                .title("게시판의 제목입니다.")
                .content("게시판의 내용입니다@@@@@@@")
                .build();
    }

    public static RequestPostUpdateDto createPostUpdateRequestDto() {
        return RequestPostUpdateDto.builder()
                .title("수정 한 제목입니다.")
                .content("수정 한 글 내용입니다@@@@@")
                .build();
    }

    public static RequestSearchPostDto createRequestSearchPostDto() {
        return RequestSearchPostDto.builder()
                .author("까까머리")
                .title("게시판의 제목")
                .content("게시판의 내용")
                .build();
    }
}
