package com.example.boardservice.module.post.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestPostUpdateDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;
}
