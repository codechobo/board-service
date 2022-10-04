package com.example.boardservice.module.post.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestPostUpdateDto {

    @NotNull
    private String title;

    @NotNull
    private String content;
}
