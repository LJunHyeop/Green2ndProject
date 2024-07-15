package com.green.fefu.score;


import com.green.fefu.score.model.InsScoreList;
import com.green.fefu.score.model.InsScoreReq;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@MybatisTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class ScoreMapperTest {

    @Autowired
    private ScoreMapper mapper;

    @Test
    void postScore() {
        InsScoreReq p = new InsScoreReq();
        p.setName("영어");
        p.setExam(1);
        p.setMark(95);
        p.setYear(2023);
        p.setScoreId(11);
        p.setSemester(1);
        p.setStudentPk(1);
        int res = mapper.postScore(p);
        System.out.println(res);
        assertEquals(1,res);
    }

    @Test
    void getScoreMidterm() {
        List<InsScoreList> list1 = new ArrayList<>();
        InsScoreList list = new InsScoreList();
        list.setScoreId(1);
        list.setStudentPk(1);
        list.setMark(95);
        list.setExam(1);
        list.setName("한글");
        list.setClassAvg(95.6);
        list.setClassRank(1);
        list.setClassStudentCount(10);
        list.setSubjectGradeRank(1);
        list.setGradeRank(1);
        list.setGradeStudentCount(20);
        list1.add(list);
        assertEquals(1,list1.size());

        List<InsScoreList> list3 = new ArrayList<>();
        InsScoreList list4 = new InsScoreList();
        list4.setScoreId(1);
        list4.setStudentPk(1);
        list4.setMark(95);
        list4.setExam(3);
        list4.setName("영어");
        list4.setClassAvg(95.6);
        list4.setClassRank(1);
        list4.setClassStudentCount(10);
        list4.setSubjectGradeRank(1);
        list4.setGradeRank(1);
        list4.setGradeStudentCount(20);
        list3.add(list4);
        assertEquals(1,list3.size());
    }

    @Test
    void getScoreFinal() {
    }

    @Test
    void getStu() {
    }

    @Test
    void getDetailScore() {
    }

    @Test
    void getDetailScoreFinal() {
    }

    @Test
    void delScore() {
    }

    @Test
    void totalList() {
    }
}