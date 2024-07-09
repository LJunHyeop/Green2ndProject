package com.green.fefu.score;

import com.green.fefu.score.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScoreMapper {
    int postScore(InsScoreReq p);

    List<InsScoreList> getScore(InsScoreReq p);

    StuGetRes getStu(long stuId);

}
