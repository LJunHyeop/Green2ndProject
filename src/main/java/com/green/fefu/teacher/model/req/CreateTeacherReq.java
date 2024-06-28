package com.green.fefu.teacher.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateTeacherReq {

    //    필수
    @Schema(example = "test1234", description = "선생님 아이디", required = true)
    private String teacherId;
    @Schema(example = "Test1234!@#$", description = "선생님 비밀번호", required = true)
    private String password;
    @Schema(example = "홍길동", description = "선생님 이름", required = true)
    private String name;
    @Schema(example = "010-0000-0000", description = "선생님 전화번호", required = true)
    private String phone;
    @Schema(example = "test1234@naver.com", description = "선생님 이메일", required = true)
    private String email;
    @Schema(example = "남 / 여", description = "선생님 성별", required = true)
    private String gender;

    //    선택
    @Schema(example = "2024-06-28", description = "선생님 생년월일")
    private String birth;
    @Schema(example = "12345", description = "선생님 우편번호")
    private String zoneCode;
    @Schema(example = "서울 판교 1234", description = "선생님 주소")
    private String addr;


    //    스웨거 출력 X
    @JsonIgnore
    private String teacherPk;
    @JsonIgnore
    private String fullAddr;
}
