package com.example.boardservice.module.post.web.dto.request;

import com.example.boardservice.module.post.domain.Post;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestPostSaveDto {

    @NotNull
    private Long categoryId; // 카테고리 아이디

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
                .author(this.author)
                .title(this.title)
                .content(this.content)
                .build();
    }
}
