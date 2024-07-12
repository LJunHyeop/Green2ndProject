package com.green.fefu.parents.fefu.score;

import com.green.fefu.score.ScoreMapper;
import com.green.fefu.score.ScoreServiceImpl;
import com.green.fefu.score.model.InsScoreReq;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(SpringExtension.class) // spring 컨테이너를 사용하고 싶음 .직접
@Import({ScoreServiceImpl.class})
class ScoreServiceTest {
    @MockBean
    private ScoreMapper mapper;
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
}