package com.example.boardservice.web;

import com.example.boardservice.module.member.domain.Member;
import com.example.boardservice.module.post.domain.Post;
import com.example.boardservice.module.post.service.PostService;
import com.example.boardservice.module.post.web.PostController;
import com.example.boardservice.module.post.web.dto.PostSaveRequestDto;
import com.example.boardservice.module.post.web.dto.PostSaveResponseDto;
import com.example.boardservice.module.post.web.dto.PostUpdateRequestDto;
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
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PostController.class)
class PostControllerTest{

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("게시글 저장한다")
    void createPost_save() throws Exception {
        // given
        Post post = createPost();
        PostSaveRequestDto postSaveRequestDto = PostSaveRequestDto.builder()
                .author(post.getAuthor())
                .title(post.getTitle())
                .build();
        PostSaveResponseDto result = PostSaveResponseDto.builder()
                .post(post)
                .build();

        given(postService.savePost(any(PostSaveRequestDto.class)))
                .willReturn(result);

        // when && then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postSaveRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(result)))
                .andExpect(jsonPath("$.title", is(result.getTitle())))
                .andExpect(jsonPath("$.content", is(result.getContent())));

        verify(postService).savePost(any(PostSaveRequestDto.class));
    }

    @Test
    @DisplayName("게시판 조회한다.")
    void readPostById() throws Exception {
        // given
        Post post = createPost();
        PostSaveResponseDto postSaveResponseDto = PostSaveResponseDto.builder()
                .post(post).build();
        given(postService.findByPostTitle(anyString())).willReturn(postSaveResponseDto);

        // when && then
        mockMvc.perform(get("/api/v1/posts/" + 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(postSaveResponseDto)));

        verify(postService).findByPostTitle(anyString());
    }

    @Test
    @DisplayName("게시판 전체 조회하다")
    void readPosts() throws Exception {
        // given
        List<Post> posts = new ArrayList<>() {{
            add(createPost());
            add(createPost());
            add(createPost());
            add(createPost());
        }};

        List<PostSaveResponseDto> result = posts.stream().map(post -> PostSaveResponseDto.builder()
                        .post(post)
                        .build())
                .collect(Collectors.toList());

        given(postService.findPosts()).willReturn(result);

        // when && then
        mockMvc.perform(get("/api/v1/posts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(result)));

        verify(postService).findPosts();
    }

    @Test
    @DisplayName("게시판 글을 수정하다")
    void updatePost() throws Exception {
        // given
        Post post = createPost();
        PostUpdateRequestDto postUpdateRequestDto = PostUpdateRequestDto.builder()
                .title("업데이트 된 제목")
                .content("업데이트 된 내용")
                .build();

        willDoNothing().given(postService)
                .updateAfterFindPost(anyLong(), any(PostUpdateRequestDto.class));

        // when && then
        mockMvc.perform(put("/api/v1/posts/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateRequestDto)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(postService).updateAfterFindPost(anyLong(), any(PostUpdateRequestDto.class));
    }

    @Test
    @DisplayName("게시판 글을 삭제한다.")
    void deletePost() throws Exception {
        // given
        willDoNothing().given(postService).removePost(anyLong());

        // when && then
        mockMvc.perform(delete("/api/v1/posts/" + 1L))
                .andDo(print())
                .andExpect(status().isOk());

        verify(postService).removePost(anyLong());
    }

    protected Post createPost() {
        Post post = Post.builder()
                .title("검정고무신 재밌지")
                .content("기영이 때문에 본다!")
                .build();
        post.addAuthor(createMember().getNickname());
        return post;
    }

    private Member createMember() {
        return Member.builder()
                .name("이기영")
                .nickname("까까머리")
                .password("test1234")
                .email("test@naver.com")
                .build();
    }
}