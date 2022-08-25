package com.example.boardservice.module.post.web.dto;

import com.example.boardservice.module.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveRequestDto {

    @NotEmpty
    @Size(max = 30)
    private String author; // 글쓴이

    @NotEmpty
    @Size(max = 100)
    private String title; // 제목

    @NotEmpty
    private String content; // 글 내용

    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
