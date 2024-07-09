package com.green.fefu.score.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JshGetReq {
    @Schema(example = "1", description = "학생Pk")
    private int stuId;
    @Schema(example = "1", description = "학년")
    private int gradle;
    @Schema(example = "01", description = "반")
    private int ban;

    @Schema(example = "2023", description = "년도")
    private int year;
    @Schema(example = "1", description = "학기")
    private int semester;
    @Schema(example = "1", description = "1:중간 2:기말  ")
    private int exam;
}
