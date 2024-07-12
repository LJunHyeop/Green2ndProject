package com.green.fefu.score.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class StuGetRes {
    private long stuId;

    @JsonIgnore
    private int latestGrade;
    @JsonIgnore
    private int latestSemester;
    @JsonIgnore
    private long latestYear;

    private int exam;
}
