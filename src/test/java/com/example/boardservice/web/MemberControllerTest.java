package com.example.boardservice.web;

import com.example.DtoInstanceProvider;
import com.example.boardservice.service.MemberService;
import com.example.boardservice.web.dto.MemberSaveRequestDto;
import com.example.boardservice.web.dto.MemberSaveResponseDto;
import com.example.boardservice.web.dto.MemberUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                DtoInstanceProvider.createMemberSaveRequestDto();

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

    @Test
    @DisplayName("Member 조회하다 -> Id 값으로")
    void readMemberById() throws Exception {
        // given
        MemberSaveRequestDto memberSaveRequestDto = DtoInstanceProvider.createMemberSaveRequestDto();
        MemberSaveResponseDto memberSaveResponseDto = MemberSaveResponseDto.builder()
                .member(memberSaveRequestDto.toEntity())
                .build();
        given(memberService.findMemberById(anyLong()))
                .willReturn(memberSaveResponseDto);

        // when && then
        mockMvc.perform(get("/api/v1/members/" + 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper
                        .writeValueAsString(memberSaveResponseDto)))
                .andExpect(jsonPath("nickname", is(memberSaveRequestDto.getNickname())));

        verify(memberService).findMemberById(anyLong());
    }

    @Test
    @DisplayName("Member 모든 정보 조회")
    void readMembers() throws Exception {
        // given
        MemberSaveRequestDto memberSaveRequestDto =
                DtoInstanceProvider.createMemberSaveRequestDto();

        MemberSaveResponseDto memberSaveResponseDto = MemberSaveResponseDto.builder()
                .member(memberSaveRequestDto.toEntity())
                .build();
        List<MemberSaveResponseDto> list = new ArrayList<>() {{
            add(memberSaveResponseDto);
        }};

        given(memberService.findMembers()).willReturn(list);

        // when && then
        mockMvc.perform(get("/api/v1/members"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(list)));

        verify(memberService).findMembers();
    }

    @Test
    @DisplayName("Member 업데이트 하다")
    void updateMember() throws Exception {
        // given
        MemberUpdateRequestDto memberUpdateRequestDto = MemberUpdateRequestDto.builder()
                .memberId(1L)
                .nickname("이기철")
                .email("기철@naver.com")
                .password("test12345")
                .build();

        willDoNothing().given(memberService)
                .updateAfterFindMember(any(MemberUpdateRequestDto.class));

        // when && then
        mockMvc.perform(put("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(memberUpdateRequestDto)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(memberService)
                .updateAfterFindMember(any(MemberUpdateRequestDto.class));
    }

    @Test
    @DisplayName("Member 삭제 한다.")
    void deleteMember() throws Exception {
        willDoNothing().given(memberService).removeMember(anyLong());

        mockMvc.perform(delete("/api/v1/members/" + 1L))
                .andDo(print())
                .andExpect(status().isOk());

        verify(memberService).removeMember(anyLong());;
    }

}