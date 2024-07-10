package com.green.fefu.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@RequiredArgsConstructor
public enum OutOfRangeErrorCode implements ErrorCode{

    NOTICE_STATE_CHECK(HttpStatus.BAD_REQUEST,"정상적이지 않은 항목을 호출하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
