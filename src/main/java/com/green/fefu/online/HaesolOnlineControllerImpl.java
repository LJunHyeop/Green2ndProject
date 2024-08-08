package com.green.fefu.online;

import com.green.fefu.common.model.ResultDto;
import com.green.fefu.online.model.GetKoreanAndMathQuestionReq;
import com.green.fefu.online.model.GetKoreanAndMathQuestionRes;
import com.green.fefu.online.model.PostOnlineQuestionReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/online")
@Tag(name = "온라인 학습-국어, 수학", description = "국어, 수학문제 CRUD")
public class HaesolOnlineControllerImpl {
    private final HaesolOnlineServiceImpl service;

    @Operation(summary = "국어 및 수학 문제 등록",
               description = "리턴 : 영향 받은 행의 값")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "정상 작동"
            ),
            @ApiResponse(responseCode = "404",
                    description = "찾을 수 없음"
            ),
            @ApiResponse(responseCode = "401",
                    description = "JWT ACCESSTOKEN 에러 ( 토큰을 헤더에 추가해 주세요 )"
            ),
            @ApiResponse(responseCode = "403",
                    description = "권한이 없는 사용자"
            )
    })
    @PostMapping("/question")
    @PreAuthorize("hasRole('TEACHER')")
    public ResultDto<Integer> PostKorAMatQuestion(@RequestPart(required = false) MultipartFile pic, @RequestPart PostOnlineQuestionReq p){
        log.info("Controller 데이터 객체 : {}", p);
        int result=service.PostKorAMatQuestion(pic, p);
        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .result(result)
                .resultMsg(HttpStatus.OK.toString())
                .build();
    }


    @GetMapping("/test")
    @Operation(summary = "문제 리스트 불러오기", description = "" +
            "<p>학생, 학부모, 선생 모두 사용 가능</p>" +
            "<p>학부모의 경우 한 학생의 pk를 <strong>무조건!!</strong> 보내줘야함</p>")
    public ResultDto<List<GetKoreanAndMathQuestionRes>> GetKorAMatQuestion(@ParameterObject GetKoreanAndMathQuestionReq p) {
        List<GetKoreanAndMathQuestionRes> list=service.GetKorAMatQuestion(p);
        return ResultDto.<List<GetKoreanAndMathQuestionRes>>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .result(list)
                .build();
    }


//    @PutMapping
//    public ResultDto<>(){
//        return ResultDto.<>builder()
//                .statusCode()
//                .resultMsg()
//                .result()
//                .build();
//    }
//
//    @DeleteMapping
//    public ResultDto<>(){
//        return ResultDto.<>builder()
//                .statusCode()
//                .resultMsg()
//                .result()
//                .build();
//    }

}
