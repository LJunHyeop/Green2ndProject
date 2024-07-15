package com.green.fefu.score;

import com.green.fefu.score.model.InsScoreList;
import com.green.fefu.score.model.InsScoreReq;
import com.green.fefu.semester.SemesterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(SpringExtension.class) // spring 컨테이너를 사용하고 싶음 .직접
@Import({ScoreServiceImpl.class})
class ScoreServiceTest {
    @MockBean
    private  ScoreMapper mapper;
    @Autowired
    private ScoreServiceImpl service;

    @Test
    void postScore() {
        InsScoreReq p = new InsScoreReq();
        p.setSemester(1);
        p.setYear(1);
        p.setExam(1);
        p.setName("김철수철수");
        p.setMark(1);
        given(mapper.postScore(p)).willReturn(1);
//        long res = service.postScore(p);
        assertEquals(1,service.postScore(p));
        verify(mapper,times(1)).postScore(p);

    }

    @Test
    void getScore() {
        List<InsScoreList> list3 = new ArrayList<>();
        InsScoreList list4 = new InsScoreList();
        list4.setScoreId(1);
        list4.setStudentPk(1);
        list4.setMark(95);
        list4.setExam(1);
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
    void getDetailScore() {
    }
}