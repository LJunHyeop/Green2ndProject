package com.green.fefu.score.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Dto <T>{
    private List<T> list = new ArrayList<T>();

    private  T studentPk;

    private int latestGrade;
    private int latestSemester;
    private long latestYear;
    private int exam;

}
