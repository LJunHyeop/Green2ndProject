package com.green.fefu.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@RequiredArgsConstructor
public enum OutOfRangeException implements ErrorCode{
    NOTICE_STATE_CHECK(HttpStatus.NOT_FOUND,"tmptmp");

    private final HttpStatus httpStatus;
    private final String message;
}
