package com.green.fefu.score.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@EqualsAndHashCode
@ToString
public class InsScoreReq {
    @JsonIgnore
    private int scoreId;
    @Schema(example = "1", description = "학생Pk")
    private int studentPk;
    @Schema(example = "2023", description = "년도")
    private int year;
    @Schema(example = "1", description = "학기")
    private int semester;
    @Schema(example = "영어", description = "과목")
    private String name;
    @Schema(example = "1", description = "1:중간 2:기말  ")
    private int exam;
    @Schema(example = "96", description = "점수")
    private int mark;
}
