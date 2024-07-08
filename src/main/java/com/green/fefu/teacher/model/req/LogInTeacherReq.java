package com.green.fefu.teacher.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LogInTeacherReq {
    @Schema(example = "test1234", description = "선생님 아이디", required = true)
    private String teacherId;
    @Schema(example = "Test1234!@#$", description = "선생님 비밀번호", required = true)
    private String password;
}
