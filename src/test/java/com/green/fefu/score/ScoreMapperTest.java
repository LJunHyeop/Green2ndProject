package com.green.fefu.score;


import com.green.fefu.score.model.InsScoreList;
import com.green.fefu.score.model.InsScoreReq;
import com.green.fefu.score.model.StuGetRes;
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
        InsScoreReq p = new InsScoreReq();
        p.setScoreId(1);
        p.setStudentPk(1);
        p.setSemester(1);
        p.setMark(96);
        p.setYear(2023);
        p.setName("국어");
        p.setGrade(1);
        p.setExam(1);
        int result = mapper.postScore(p) ;
        System.out.println(p);

        StuGetRes res = new StuGetRes();
        res.setExam(1);
        res.setStudentPk(1);
        res.setLatestGrade(1);
        res.setLatestYear(1);
        res.setLatestYear(2023);
        System.out.println(mapper.getScoreMidterm(res));
        List<InsScoreList> list1 = mapper.getScoreMidterm(res) ;
        assertEquals(result, list1.size());
    }

    @Test
    void getScoreFinal() {

        List<InsScoreList> list3 = new ArrayList<>();

        InsScoreList list4 = new InsScoreList();
        list4.setScoreId(1);
        list4.setStudentPk(1);
        list4.setMark(95);
        list4.setExam(2);
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
    void getStu() {
        StuGetRes p = new StuGetRes();
        p.setLatestGrade(1);
        p.setLatestSemester(1);
        p.setLatestYear(2023);
        p.setStudentPk(1);
        p.setExam(1);
        List<StuGetRes> res1 = new ArrayList<>();
        res1.add(mapper.getStu(p.getStudentPk()));
        assertEquals(1,res1.size());
    }

    @Test
    void getDetailScore() {
        List<InsScoreList> list3 = new ArrayList<>();
        InsScoreList list4 = new InsScoreList();
        list4.setScoreId(1);
        list4.setStudentPk(1);
        list4.setMark(95);
        list4.setExam(2);
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
    void getDetailScoreFinal() {
        List<InsScoreList> list3 = new ArrayList<>();
        InsScoreList list4 = new InsScoreList();
        list4.setScoreId(1);
        list4.setStudentPk(1);
        list4.setMark(95);
        list4.setExam(2);
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
    void delScore() {

    }

    @Test
    void totalList() {
    }
}