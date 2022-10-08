package com.example.boardservice.error;

import com.example.boardservice.error.response.ErrorResponseDto;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RestControllerAdvice
public class BoardServiceExceptionAdvice {

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> entityNotFoundException(EntityNotFoundException e) {

        final ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .httpStatus(ErrorCode.NOT_FOUND_ENTITY.getStatus())
                .message(ErrorCode.NOT_FOUND_ENTITY.getMessage())
                .build();

        log.info("EntityNotFoundException : {}", e);
        return ResponseEntity.status(ErrorCode.NOT_FOUND_ENTITY.getStatus()).body(errorResponseDto);
    }

    @ExceptionHandler(value = DuplicateRequestException.class)
    public ResponseEntity<ErrorResponseDto> duplicateRequestException(DuplicateRequestException e) {

        final ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .httpStatus(ErrorCode.REQUEST_DATA_DUPLICATED.getStatus())
                .message(ErrorCode.REQUEST_DATA_DUPLICATED.getMessage())
                .build();

        log.info("DuplicateRequestException : {}", e);
        return ResponseEntity.status(ErrorCode.REQUEST_DATA_DUPLICATED.getStatus()).body(errorResponseDto);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> illegalArgumentException(IllegalArgumentException e) {
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .build();
        return ResponseEntity.internalServerError().body(errorResponseDto);
    }
}
