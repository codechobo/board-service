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


    @WithMockUser
    @Test
    @DisplayName("회원 정보를 저장하는데 중복된 정보가 저장되면 예외가 발생하며 저장이 되지 않는다.")
    void createMember_Exception_Fail() throws Exception {
        RequestMemberSaveDto requestDto = RequestDtoFactory.createMemberSaveRequestDto();
        memberRepository.save(requestDto.toEntity());

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is(ErrorCode.REQUEST_DATA_DUPLICATED.getStatus().value()));
    }

    @WithMockUser
    @Test
    @DisplayName("엔티티를 조회하는데 성공한다.")
    void readMemberById_Success() throws Exception {
        // given
        RequestMemberSaveDto requestDto = RequestDtoFactory.createMemberSaveRequestDto();
        Member member = requestDto.toEntity();

        Member savedMember = memberRepository.save(member);
        ResponseMemberSaveDto memberSaveResponseDto = ResponseMemberSaveDto.builder().member(member).build();

        // when && then
        mockMvc.perform(get("/members/" + savedMember.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(memberSaveResponseDto)));
    }
}