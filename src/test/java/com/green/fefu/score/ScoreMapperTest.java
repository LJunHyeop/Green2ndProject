package com.green.fefu.score;

import com.green.fefu.score.model.InsScoreReq;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@MybatisTest
@ActiveProfiles("tdd")
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
        p.setStuId(1);
        p.setScoreId(182);
        p.setSemester(1);

        int res = mapper.postScore(p);
        System.out.println(res);
        assertEquals(1,res);


    }
//        SemesterReq p = new SemesterReq();
//        p.setSemesterId(1);
//        p.setOption(1);
//
//        long res = mapper.postSemester(p);
//        assertEquals(1, res);
//        SemesterReq p1 = new SemesterReq();
//        p1.setSemesterId(2);
//        p1.setOption(2);
//
//        long res1= mapper.postSemester(p1);
//        assertEquals(1, res1);
//    }
}