package com.green.fefu.score.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class RankRes extends RankReq{
    private int classRank;

    private int gradeRank;

    private int classStudentCount;

    private int gradeStudentCount;
}
