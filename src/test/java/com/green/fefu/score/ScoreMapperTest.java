package com.green.fefu.score;


import com.green.fefu.score.model.GetDetailScoreReq;
import com.green.fefu.score.model.InsScoreList;
import com.green.fefu.score.model.InsScoreReq;
import com.green.fefu.score.model.StuGetRes;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.PostMapping;

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
        p.setStudentPk(1);
        p.setSemester(1);
        p.setMark(96);
        p.setYear(2023);
        p.setName("국어");
        p.setGrade(1);
        p.setExam(1);

        System.out.println(p);


        StuGetRes res = new StuGetRes();
        res.setExam(1);
        res.setStudentPk(p.getStudentPk());
        res.setLatestGrade(p.getGrade());
        res.setLatestSemester(p.getSemester());
        res.setLatestYear(p.getYear());
        res.setScoreId(p.getScoreId());
        System.out.println(res);
        List<InsScoreList> list1 = mapper.getScoreMidterm(res) ;
        assertEquals(1, list1.size());
    }

    @Test
    void getScoreFinal() {
        InsScoreReq p = new InsScoreReq();
        p.setStudentPk(1);
        p.setSemester(1);
        p.setMark(96);
        p.setYear(2023);
        p.setName("국어");
        p.setGrade(1);
        p.setExam(2);
        System.out.println(p);
        StuGetRes res = new StuGetRes();
        res.setExam(2);
        res.setStudentPk(p.getStudentPk());
        res.setLatestGrade(p.getGrade());
        res.setLatestSemester(p.getSemester());
        res.setLatestYear(p.getYear());
        res.setScoreId(p.getScoreId());
        System.out.println(res);
        List<InsScoreList> list1 = mapper.getScoreFinal(res) ;
        assertEquals(0, list1.size());

    }

    @Test
    void getStu() {
        StuGetRes res = new StuGetRes();
        res.setExam(1);
        res.setStudentPk(1);
        res.setLatestGrade(1);
        res.setLatestYear(1);
        res.setLatestYear(2023);
        res.setScoreId(72);
        List<StuGetRes> list1 = new ArrayList<>() ;
        list1.add(mapper.getStu(res.getStudentPk()));
        assertEquals(1, list1.size());
    }

    @Test
    void getDetailScore() {
//        InsScoreReq p = new InsScoreReq();
//        p.setScoreId(1);
//        p.setStudentPk(1);
//        p.setSemester(1);
//        p.setMark(96);
//        p.setYear(2023);
//        p.setName("국어");
//        p.setGrade(2);
//        p.setExam(1);
//        System.out.println(p);
        GetDetailScoreReq res = new GetDetailScoreReq();
        res.setExam(1);
        res.setStudentPk(2);
        res.setScoreId(1);
        res.setGrade(2);
        res.setSemester(1);
        System.out.println(res);
        List<InsScoreList> list1 = mapper.getDetailScore(res) ;
        assertEquals(0, list1.size());
    }

    @Test
    void getDetailScoreFinal() {
        GetDetailScoreReq res = new GetDetailScoreReq();
        res.setExam(2);
        res.setStudentPk(2);
        res.setScoreId(1);
        res.setGrade(2);
        res.setSemester(1);
        System.out.println(res);
        List<InsScoreList> list1 = mapper.getDetailScoreFinal(res) ;
        assertEquals(0, list1.size());
    }

    @Test
    void delScore() {
        GetDetailScoreReq res = new GetDetailScoreReq();
        res.setExam(2);
        res.setStudentPk(2);
        res.setScoreId(1);
        res.setGrade(2);
        res.setSemester(1);
        System.out.println(res);
        List<InsScoreList> list1 = mapper.getDetailScoreFinal(res) ;
        assertEquals(0, list1.size());
    }

    @Test
    void totalList() {
    }
}