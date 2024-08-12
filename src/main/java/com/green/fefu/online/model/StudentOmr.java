package com.green.fefu.online.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class StudentOmr { //프론트가 나에게 넘겨줄 값
    @Schema(name="List(문제의 PK번호)", example = "[1,5,2,6,8 ...]")
    private Long questionPk; //현재 화면에 출력되고 있는 문제의 PK값 리스트
    @Schema(name="List(학생의 마킹 번호)", example = "[3,4,2,1,2,4 ...]")
    private int omrAnswer; //학생이 제출 OMR

    @JsonIgnore
    private int realAnswer; //실제 정답 번호
}
