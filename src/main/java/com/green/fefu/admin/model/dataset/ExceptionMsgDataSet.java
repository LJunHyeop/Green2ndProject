package com.green.fefu.admin.model.dataset;

public interface ExceptionMsgDataSet {
    String DIVISION_ERROR = "분류 코드가 잘못되었습니다.";
    String QUERY_RESULT_ERROR = "쿼리 에러";
    String COOKIE_NOT_FOUND_ERROR = "쿠키에 저장된 데이터가 없습니다.";
    String RE_FRESH_TOKEN_TIME_OUT_ERROR = "리프레쉬 토큰의 만료시간이 초과하였습니다.";
}
