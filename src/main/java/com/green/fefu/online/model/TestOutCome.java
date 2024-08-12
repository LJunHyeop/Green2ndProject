package com.green.fefu.online.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class TestOutCome { // 틀린 문제만 리턴-> 프론트 요구시 수정
    //이너클래스는 뭔가 다른가
    private List<StudentOmr> markOmrList=new ArrayList<>();
    private String message;

}
