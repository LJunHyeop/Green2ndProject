package com.green.fefu.online.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetKoreanAndMathQuestionReq {
    //과목, 학년, 난이도,
    private Long studentPk;
    private Long subject;
}
