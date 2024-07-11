package com.green.fefu.score;

import com.green.fefu.common.ResultDto;
import com.green.fefu.score.model.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @PreAuthorize("hasRole('TEAHCER')")
    public ResultDto<Long> postScore(@RequestBody  InsScoreReq p){
        long res = service.postScore(p);
        try {
            return ResultDto.resultDto(SUCCESS_CODE,"점수 입력 성공",res);
        }catch (RuntimeException e){
            return ResultDto.resultDto1(ERROR_CODE,"점수 입력 실패");
        }
    }
    @GetMapping("/getScore")
    @Operation(summary = "학생성적조회 최초 성적조회 화면 이동시 ")
    @PreAuthorize("hasRole('TEAHCER') or hasRole('PARENTS')")
    public ResultDto<Dto> getScore(@ParameterObject @ModelAttribute StuGetRes p){
        try {
            Dto res = service.getScore(p);
            if(res.getList().size() == 0){
                return null;
            }
            return ResultDto.resultDto(SUCCESS_CODE,"성적조회성공",res);
        }catch (RuntimeException e){
            return ResultDto.resultDto1(ERROR_CODE,"성적조회 실패");
        }
    }
    @GetMapping("/getScoreDetail")
    @Operation(summary = "학생성적조회 학년과 학기 조회시")
    public ResultDto<DtoDetail> getDetailScore(@ParameterObject @ModelAttribute GetDetailScoreReq p){
        try {
            DtoDetail res = service.getDetailScore(p);
            if(res.getList().size() == 0 ){
                return null;
            }
            return ResultDto.resultDto(SUCCESS_CODE,"성적조회성공",res);
        }catch (RuntimeException e){
            return ResultDto.resultDto(ERROR_CODE,"성적조회실패",null);
        }
    }
}
