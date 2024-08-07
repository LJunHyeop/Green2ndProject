package com.green.fefu.online.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetKoreanAndMathQuestionReq {
    //과목, 학년, 난이도
    private Long studentPk; // 학년 정보를 가져옴
    private Long subject; // 과목
    private Long level;
}
