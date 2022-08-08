package com.example.boardservice.error.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {

    @JsonProperty
    private final LocalDateTime time = LocalDateTime.now();

    @JsonProperty("http_status")
    private final HttpStatus httpStatus;

    @JsonProperty
    private final String message;

    @JsonProperty("field_errors")
    private List<FieldError> fieldErrors;

    @Builder
    public ErrorResponseDto(HttpStatus httpStatus, String message, List<FieldError> fieldErrors) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }
}
