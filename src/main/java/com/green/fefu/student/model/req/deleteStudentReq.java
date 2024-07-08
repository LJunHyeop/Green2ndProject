package com.green.fefu.student.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class deleteStudentReq {
    @Schema(example = "1",description = "삭제할 유저 pk값", required = true)
    private Long pk;
    @Schema(example = "2",description = "변경할 값 ( 1 -> 재학중, 2 -> 졸업, 3 -> 전학 )", required = true)
    private Integer state;
}
