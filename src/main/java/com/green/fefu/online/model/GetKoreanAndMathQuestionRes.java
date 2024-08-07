package com.green.fefu.online.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GetKoreanAndMathQuestionRes {
    private long queId;
    // question Entity
    private String question;
    private String level;
    private int typeTag;
    private int queTag;
    private String contents;
    private int answer;
    private String pic;

    // multiple Entity
    private List<Integer> num;
    private List<String> sentence=new ArrayList<>(5);



}
