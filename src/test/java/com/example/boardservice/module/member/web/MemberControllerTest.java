package com.example.boardservice.module.member.web;

import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.factory.RequestDtoFactory;
import com.example.boardservice.module.member.domain.Member;
import com.example.boardservice.module.member.domain.repository.MemberRepository;
import com.example.boardservice.module.member.service.MemberService;
import com.example.boardservice.module.member.web.dto.request.RequestMemberSaveDto;
import com.example.boardservice.module.member.web.dto.response.ResponseMemberSaveDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired ObjectMapper objectMapper;

    @WithMockUser
    @Test
    @DisplayName("회원 정보 저장하는데 성공한다.")
    void createMember_success() throws Exception {
        // given
        RequestMemberSaveDto requestDto = RequestDtoFactory.createMemberSaveRequestDto();

        // when && then
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("회원 정보를 저장하는데 중복된 정보가 저장되면 예외가 발생하며 저장이 되지 않는다.")
    void createMember_Exception_Fail() throws Exception {
        RequestMemberSaveDto memberSaveRequestDto = RequestMemberSaveDto.builder()
                .name("이기영")
                .nickname("까까머리")
                .email("기영@naver.com")
                .password("test1234")
                .build();
        given(memberService.saveMember(any(RequestMemberSaveDto.class)))
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
        RequestMemberSaveDto memberSaveRequestDto = RequestMemberSaveDto.builder()
                .name("이기영")
                .nickname("까까머리")
                .email("기영@naver.com")
                .password("test1234")
                .build();
        Member member = memberSaveRequestDto.toEntity();
        ResponseMemberSaveDto memberSaveResponseDto = ResponseMemberSaveDto.builder().member(member).build();
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