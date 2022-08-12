package com.example.boardservice.web;

import com.example.boardservice.domain.Post;
import com.example.boardservice.service.PostService;
import com.example.boardservice.service.PostServiceTest;
import com.example.boardservice.web.dto.post_dto.PostSaveRequestDto;
import com.example.boardservice.web.dto.post_dto.PostSaveResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostController.class)
class PostControllerTest extends PostServiceTest {

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

        // when
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postSaveRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));

        verify(postService).savePost(any(PostSaveRequestDto.class));
    }

}