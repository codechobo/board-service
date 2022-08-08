package com.example.boardservice.error;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND_ENTITY(HttpStatus.NOT_FOUND, "Not Found Entity !!"),
    REQUEST_DATA_DUPLICATED(HttpStatus.BAD_REQUEST, "Duplicated Data !!");

    private final HttpStatus status;
    private final String message;

}
