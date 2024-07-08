package com.green.fefu.score;

import com.green.fefu.score.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.DTD;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScoreServiceImpl {
    private final ScoreMapper mapper;

    public long postScore(InsScoreReq p ){
        return mapper.postScore(p);
    }
    public Dto getScore(InsScoreReq p){
        Dto dto = new Dto();
        StuGetRes res = mapper.getStu(p.getScId());
        if(p.getSemester() == 0 ){
            res.getLatestSemester();
            p.setSemester(res.getLatestSemester());
        }
        dto.setList(mapper.getScore(p));
        dto.setStuId(mapper.getStu(p.getScId()));
        return dto;
    }
}
