package com.green.fefu.parents.fefu.score;


import com.green.fefu.score.ScoreMapper;
import com.green.fefu.score.model.InsScoreReq;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.assertEquals;


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
        p.setScoreId(182);
        p.setSemester(1);
        int res = mapper.postScore(p);
        System.out.println(res);
        assertEquals(1,res);


    }


}