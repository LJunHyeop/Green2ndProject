package com.green.fefu.exception.JSH;

import com.green.fefu.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@RequiredArgsConstructor
public enum JshErrorCode implements ErrorCode {
    HOMEROOM_ISN_T_EXIST(HttpStatus.BAD_REQUEST, "각 학년의 담임 선생님만 출제할 수 있습니다."),
    CAN_T_UPROAD_QUESTION(HttpStatus.BAD_REQUEST, "문제를 등록하지 못 했습니다"),
    NOT_FOUND_QUESTION(HttpStatus.BAD_REQUEST, "문제를 불러올 수 없습니다."),
    STUDENT_PK_NOT_FOUND_ERROR(HttpStatus.BAD_REQUEST, "학생 데이터 에러"),
    CAN_T_GET_GRADE(HttpStatus.BAD_REQUEST, "학년 정보를 읽어올 수 없습니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
