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


    private int classRank;


    private double schoolAvg;


    private int schoolRank;


    private double midtermAvg;


    private  double final_avg;


    private double subjectAvg;

}
