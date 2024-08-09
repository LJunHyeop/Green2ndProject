package com.green.fefu.online.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetEnglishWordsQuestionReq {
    //학생만 풀 수 있는지 학부모도 접근 가능한지
    //학년에 따라 다르게 넣기?
    @Schema(example = "1", description = "학부모의 경우 한 명의 자녀 PK가 필요함")
    private Long studentPk;
}
