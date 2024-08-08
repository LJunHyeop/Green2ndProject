package com.green.fefu.exception.JSH;

import com.green.fefu.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@RequiredArgsConstructor
public enum JshErrorCode implements ErrorCode {
    STUDENT_PK_NOT_FOUND_ERROR(HttpStatus.BAD_REQUEST, "학생 데이터 에러"),
    CANT_ENTER(HttpStatus.BAD_REQUEST, "접근 권한이 없습니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
