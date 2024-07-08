package com.green.fefu.score;

import com.green.fefu.common.ResultDto;
import com.green.fefu.score.model.InsScoreReq;

public interface ScoreController {
    public ResultDto<Long> postScore(InsScoreReq p);
}
