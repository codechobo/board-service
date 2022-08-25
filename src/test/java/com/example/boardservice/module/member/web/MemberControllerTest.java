package com.example.boardservice.module.member.web;

import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.module.member.domain.Member;
import com.example.boardservice.module.member.service.MemberService;
import com.example.boardservice.module.member.web.dto.request.MemberSaveRequestDto;
import com.example.boardservice.module.member.web.dto.response.MemberSaveResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("회원 정보 저장하는데 성공한다.")
    void createMember_Success() throws Exception {
        // given
        MemberSaveRequestDto memberSaveRequestDto = MemberSaveRequestDto.builder()
                .name("이기영")
                .nickname("까까머리")
                .email("기영@naver.com")
                .password("test1234")
                .build();
        Member member = memberSaveRequestDto.toEntity();
        MemberSaveResponseDto memberSaveResponseDto = MemberSaveResponseDto.builder().member(member).build();
        given(memberService.saveMember(any(MemberSaveRequestDto.class))).willReturn(memberSaveResponseDto);

        // when && then
        mockMvc.perform(post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberSaveRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(memberSaveResponseDto)))
                .andExpect(jsonPath("$.nickname").value(memberSaveResponseDto.getNickname()));
    }

    @Test
    @DisplayName("회원 정보를 저장하는데 중복된 정보가 저장되면 예외가 발생하며 저장이 되지 않는다.")
    void createMember_Exception_Fail() throws Exception {
        MemberSaveRequestDto memberSaveRequestDto = MemberSaveRequestDto.builder()
                .name("이기영")
                .nickname("까까머리")
                .email("기영@naver.com")
                .password("test1234")
                .build();
        given(memberService.saveMember(any(MemberSaveRequestDto.class)))
                .willThrow(new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));


        mockMvc.perform(post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberSaveRequestDto)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.REQUEST_DATA_DUPLICATED.getStatus().value()));
    }

    @Test
    @DisplayName("엔티티를 조회하는데 성공한다.")
    void readMemberById_Success() throws Exception {
        // given
        MemberSaveRequestDto memberSaveRequestDto = MemberSaveRequestDto.builder()
                .name("이기영")
                .nickname("까까머리")
                .email("기영@naver.com")
                .password("test1234")
                .build();
        Member member = memberSaveRequestDto.toEntity();
        MemberSaveResponseDto memberSaveResponseDto = MemberSaveResponseDto.builder().member(member).build();
        given(memberService.findMemberById(anyLong())).willReturn(memberSaveResponseDto);

        // when && then
        mockMvc.perform(get("/api/v1/members/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(memberSaveResponseDto)));
    }

    @Test
    @DisplayName("엔티티를 찾지 못해 예외가 발생하며 조회에 실패한다.")
    void readMemberById_Fail() throws Exception {
        // given
        given(memberService.findMemberById(anyLong()))
                .willThrow(new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));

        // when && then
        mockMvc.perform(get("/api/v1/members/1"))
                .andDo(print())
                .andExpect(status().is(ErrorCode.NOT_FOUND_ENTITY.getStatus().value()))
                .andExpect(result ->
                        assertTrue(Objects.requireNonNull(result.getResolvedException())
                                .getClass().isAssignableFrom(EntityNotFoundException.class)))
                .andExpect(result ->
                        assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                                ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }
}