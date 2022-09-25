package com.example.boardservice.module.member.exception;

import com.example.boardservice.error.BusinessException;
import com.example.boardservice.error.ErrorCode;

public class NotMatchPasswordException extends BusinessException {
    public NotMatchPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
