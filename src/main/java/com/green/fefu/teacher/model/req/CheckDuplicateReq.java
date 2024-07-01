package com.green.fefu.teacher.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CheckDuplicateReq {
    @Schema(example = "test1234", description = "선생님 아이디")
    private String teacherId;
    @Schema(example = "test1234@naver.com", description = "선생님 이메일")
    private String email;
}
