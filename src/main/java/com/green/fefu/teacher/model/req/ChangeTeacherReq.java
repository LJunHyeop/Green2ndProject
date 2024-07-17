package com.green.fefu.teacher.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChangeTeacherReq {
    @Schema(example = "홍길동", description = "선생님 이름")
    private String name;
    @Schema(example = "010-0000-0000", description = "선생님 전화번호")
    private String phone;
    @Schema(example = "test1234@naver.com", description = "선생님 이메일")
    private String email;
    @Schema(example = "12345", description = "선생님 우편번호")
    private String zoneCode;
    @Schema(example = "서울 판교 1234", description = "선생님 주소")
    private String addr;
    @Schema(example = "101동", description = "선생님 상세주소")
    private String detail;

    @JsonIgnore
    private long pk;
    @JsonIgnore
    private String fullAddr;
}
