package com.green.fefu.student.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class updateStudentReq {
    @Schema(example = "홍길동", description = "학생 이름")
    private String studentName;
    @Schema(example = "010-0000-0000", description = "전화번호")
    private String studentPhone;
    @Schema(example = "서울 판교로 112", description = "주소 + 상세주소")
    private String studentAddr;
    @Schema(example = "1234", description = "우편번호")
    private String studentZoneCode;
    @Schema(example = "101동",description = "상세 주소")
    private String studentDetail;
    @Schema(example = "갑각류 알러지 있음", description = "특이사항 기입")
    private String studentEtc;
    @Schema(example = "1", description = "바꿀 학생의 pk값", required = true)
    private long studentPk;
    @Schema(example = "1999-01-01", description = "학생 생년월일")
    private String studentBirth;

    @JsonIgnore
    private String fullAddr;

    public void setFullAddr() {
        this.fullAddr = studentZoneCode + "#" + studentAddr;
    }
}
