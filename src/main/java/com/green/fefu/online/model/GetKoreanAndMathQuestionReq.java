package com.green.fefu.online.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetKoreanAndMathQuestionReq {
    //과목, 학년, 난이도
    @JsonIgnore
    private Long studentPk; // 학년 정보를 가져옴

    private Long subjectCode; // 과목
    private Long level;
}
