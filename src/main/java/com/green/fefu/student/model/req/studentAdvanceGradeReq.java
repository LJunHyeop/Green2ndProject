package com.green.fefu.student.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class studentAdvanceGradeReq {
    @Schema(example = "1", description = "학생 pk", required = true)
    private String studentPk;
    @Schema(example = "10101", description = "진학 하는 학년/반/번호", required = true)
    private String grade;

    @JsonIgnore
    private String subNumber;
    @JsonIgnore
    private String etc;
}
