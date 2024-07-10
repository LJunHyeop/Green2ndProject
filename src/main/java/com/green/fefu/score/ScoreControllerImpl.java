package com.green.fefu.score;

import com.green.fefu.common.ResultDto;
import com.green.fefu.score.model.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.green.fefu.common.GlobalConst.ERROR_CODE;
import static com.green.fefu.common.GlobalConst.SUCCESS_CODE;
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/Score")
public class ScoreControllerImpl implements ScoreController  {

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
    @Operation(summary = "학생성적조회 최초 성적조회 화면 이동시 ", description = "리턴 => 학생 ,과목 ,중간기말, 반평균 , 반등수 , 학교평균 ," +
            "학교등수, 중간고사평균 ,기말 평균 , 과목평균   , stu , 마지막 학년 , 마지막 학기 , 마지막 년도  ")
    public ResultDto<Dto> getScore(@ParameterObject @ModelAttribute GetScoreReq p){
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
    @GetMapping("/getScoreDetail")
    @Operation(summary = "학생성적조회 학년과 학기 조회시 ", description = "리턴 => 학생 ,과목 ,중간기말, 반평균 , 반등수 , 학교평균 ," +
            "학교등수, 중간고사평균 ,기말 평균 , 과목평균 ")
    public ResultDto<DtoDetail> getDetailScore(@ParameterObject @ModelAttribute GetDetailScoreReq p){
        DtoDetail res = service.getDetailScore(p);
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
