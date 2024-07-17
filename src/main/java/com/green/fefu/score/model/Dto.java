package com.green.fefu.score.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Dto <T>{
    private List<T> list = new ArrayList<T>();

    private  T studentPk;

    private int latestGrade;
    private int latestSemester;
    private String latestYear;



    private T classRank;

//    private T gradeRank;
//
//    private T classStudentCount;
//
//    private T gradeStudentCount;


}
