package com.example.boardservice.web;

import com.example.boardservice.MemberInstanceProvider;
import com.example.boardservice.domain.Member;
import com.example.boardservice.service.MemberService;
import com.example.boardservice.web.dto.MemberSaveRequestDto;
import com.example.boardservice.web.dto.MemberSaveResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Member 정보 저장한다.")
    void createMember() throws Exception {
        // given
        MemberSaveRequestDto memberSaveRequestDto =
                MemberInstanceProvider.createMemberSaveRequestDto();

        given(memberService.saveMember(any(MemberSaveRequestDto.class)))
                .willReturn(MemberSaveResponseDto.builder()
                        .member(memberSaveRequestDto.toEntity())
                        .build());

        // when && then
        mockMvc.perform(post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberSaveRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(
                                MemberSaveResponseDto
                                        .builder()
                                        .member(memberSaveRequestDto.toEntity())
                                        .build())));

        verify(memberService).saveMember(any(MemberSaveRequestDto.class));
    }

}