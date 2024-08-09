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
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "국어 및 수학 문제 등록", description = "" +
            "<p><strong>subjectCode</strong> (정수)과목 코드 1: 국어 2: 수학</p>" +
            "<p><strong>typeTag</strong> (정수)세부 과목 ex. 국어 : 11, 12, 13 / 수학 : 21, 22, 23, 24, 25</p>" +
            "<p><strong>queTag</strong> (정수)문제 유형 1: 객관식 2: 주관식 (Default 1)</p>" +
            "<p><strong>level</strong> (정수)난이도 1~5</p>" +
            "<p><strong>question</strong> (문자열)문제 ex. 다음 중 옳은 것을 고르시오</p>" +
            "<p><strong>content</strong> (문자열)내용 ex. 먼 훗날 당신이 찾으시면 그때에 내 말이 \"잊었노라\"</p>" +
            "<p><strong>multiple</strong> (문자열 리스트)보기 리스트 사과 딸기 바나나 청포도 배(숫자 없이 문자만)</p>" +
            "<p><strong>answer</strong> (정수)정답이 되는 보기 번호 ex.1")
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
            "<p><strong>studentPk</strong> 학생 1명의 PK</p>" +
            "<p><strong>subjectCode</strong> 국어/수학 과목 선택</p>" +
            "<p>학생, 학부모, 선생 모두 사용 가능</p>" +
            "<p>학부모의 경우 한 학생의 pk를 <strong>무조건!!</strong> 보내줘야함</p>")
    public ResponseEntity GetKorAMatQuestion(@ParameterObject GetKoreanAndMathQuestionReq p) {
        List<GetKoreanAndMathQuestionRes> list=service.GetKorAMatQuestion(p);

        log.info("컨트롤러 리턴");
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary="시험 마킹 결과 및 채점 결과 확인")
    public ResponseEntity testMarking(){
        List<GetKoreanAndMathQuestionRes> test;
        // 시험 점수 + 분야별 어느거 많이 틀렸는지
        return new ResponseEntity(null);
    }



}
