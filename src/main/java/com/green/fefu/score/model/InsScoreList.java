package com.green.fefu.score.model;

import lombok.Data;

@Data
public class InsScoreList {
    private String name;
    private int exam;
    private int mark;
    private Integer scoreId;
    private int studentPk;
    private double classAvg;
    private double gradeAvg;

    private int subjectGradeRank;

    private double classStudentCount;

    private int classRank;

    private int gradeRank;

    private double gradeStudentCount;

}
