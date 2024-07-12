package com.green.fefu.score.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StuGetRes {
    private long studentPk;

    @JsonIgnore
    private int latestGrade;
    @JsonIgnore
    private int latestSemester;
    @JsonIgnore
    private long latestYear;

    private int exam;
}
