package com.example.boardservice.module.category.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@AllArgsConstructor
public class RequestCategorySaveDto {

    @NotEmpty
    @JsonProperty("category_name")
    private String categoryName;

}
