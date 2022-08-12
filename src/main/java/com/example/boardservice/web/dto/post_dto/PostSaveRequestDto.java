package com.example.boardservice.web.dto.post_dto;

import com.example.boardservice.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveRequestDto {

    @NotNull
    @Size(max = 30)
    private String author; // 글쓴이

    @NotNull
    @Size(max = 100)
    private String title; // 제목

    @NotNull
    private String content; // 글 내용

    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
