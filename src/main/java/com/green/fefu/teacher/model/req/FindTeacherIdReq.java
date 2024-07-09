package com.green.fefu.teacher.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindTeacherIdReq {
    @Schema(example = "홍길동", description = "선생님 이름", required = true)
    private String name;
    @Schema(example = "010-0000-0000", description = "선생님 전화번호", required = true)
    private String phone;
}
