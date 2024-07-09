package com.green.fefu.score;

import com.green.fefu.common.ResultDto;
import com.green.fefu.score.model.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.green.fefu.common.GlobalConst.ERROR_CODE;
import static com.green.fefu.common.GlobalConst.SUCCESS_CODE;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/Score")
public class ScoreControllerImpl implements ScoreController {

    private final ScoreServiceImpl service;

    @PostMapping
    @Operation(summary = "점수 입력 칸 ")
    public ResultDto<Long> postScore(@RequestBody InsScoreReq p){
        long res = service.postScore(p);
        try {
            return ResultDto.resultDto(SUCCESS_CODE,"점수 입력 성공",res);
        }catch (RuntimeException e){
            return ResultDto.resultDto1(ERROR_CODE,"점수 입력 실패");
        }
    }
    @GetMapping("/getScore")
    @Operation(summary = "학생 성적 조회")
    public ResultDto<Dto> getScore(@ParameterObject @ModelAttribute InsScoreReq p){
        Dto res = service.getScore(p);
        try {
            if(res.getList().size() == 0){
                return null;
            }
            return ResultDto.resultDto(SUCCESS_CODE,"성적조회성공",res);
        }catch (RuntimeException e){
            return ResultDto.resultDto1(ERROR_CODE,"성적조회 실패");
        }
    }
}
