package com.green.fefu.student.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class createStudentReq {
    @Schema(example = "10101", description = "학생 학년 반 번호 ( 1학년 1반 1번 )", required = true)
    private String grade;
    @Schema(example = "홍길동", description = "학생 이름", required = true)
    private String name;
    @Schema(example = "남/여", description = "학생 성별", required = true)
    private String gender;
    @Schema(example = "1998-01-01", description = "학생 생년월일", required = true)
    private String birth;
    @Schema(example = "123 # 서울 판교로 112", description = "학생 주소", required = true)
    private String addr;

    @Schema(example = "갑각류 알러지 있음", description = "학생 추가정보 기입", required = true)
    private String etc;
    @Schema(example = "adela", description = "학생 영어이름", required = true)
    private String engName;
    @Schema(example = "010-0000-0000", description = "학생 전화번호", required = true)
    private String phone;

    @Schema(example = "1", description = "선생 pk (반배정 안된 선생만 넣기!!!!)")
    private long teacherPk;
    @JsonIgnore
    private long pk;
    @JsonIgnore
    private String pic;
}
