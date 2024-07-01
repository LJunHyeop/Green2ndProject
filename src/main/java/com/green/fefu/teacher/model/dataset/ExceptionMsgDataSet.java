package com.green.fefu.teacher.model.dataset;

public interface ExceptionMsgDataSet {
    String BIRTH_TYPE_ERROR = "생년월일 입력 형식 에러";
    String ID_PATTERN_ERROR = "아이디 형식을 맞춰주세요";
    String EMAIL_PATTERN_ERROR = "이메일 형식을 맞춰주세요";
    String NAME_PATTERN_ERROR = "이름 형식을 맞춰주세요";
    String PASSWORD_PATTERN_ERROR = "비밀번호 형식을 맞춰주세요";
    String PHONE_PATTERN_ERROR = "휴대폰 번호 형식을 맞춰주세요";
    String QUERY_RESULT_ERROR = "쿼리 에러";
    String ID_NOT_FOUND_ERROR = "아이디가 일치하지 않습니다.";
    String PASSWORD_NO_MATCH_ERROR = "비밀번호가 일치하지 않습니다.";
    String ESSENTIAL_DATA_NOT_FOUND_ERROR = "필수 데이터가 없습니다.";
    String DUPLICATE_DATA_ERROR = "이미 사용 중 입니다.";
    String NOT_FOUND_USER_ERROR = "유저 정보가 없습니다.";
    String SMS_SEND_ERROR = "문자 메세지 보내기 실패";
}
