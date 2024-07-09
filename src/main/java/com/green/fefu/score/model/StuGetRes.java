package com.green.fefu.score.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StuGetRes {
    private long stuId;
    private int latestGrade;
    private int latestSemester;
    private long latestYear;
}
