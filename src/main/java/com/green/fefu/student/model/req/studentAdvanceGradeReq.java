package com.green.fefu.student.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class studentAdvanceGradeReq {
    private String studentPk;
    private String grade;

    @JsonIgnore
    private String subNumber;
}
