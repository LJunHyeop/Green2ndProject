package com.green.fefu.online.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetKoreanAndMathQuestionRes {
    // question Entity
    private String question;
    private String level;
    private int typeTag;
    private int queTag;
    private String contents;
    private int answer;

    // multiple Entity
    private List<Integer> num;
    private List<String> sentence;

    // pic Entity
    private String pic;

}
