package com.green.fefu.online;

import com.green.fefu.common.model.ResultDto;
import com.green.fefu.online.model.GetKoreanAndMathQuestionReq;
import com.green.fefu.online.model.GetKoreanAndMathQuestionRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class OnlineGetControllerImpl {
    @PostMapping("korean")
    public ResultDto<List<GetKoreanAndMathQuestionRes>> getKoreanQuestion(GetKoreanAndMathQuestionReq p){
        return ResultDto.<List<GetKoreanAndMathQuestionRes>>builder()
                .build();
    }
    @PostMapping("math")
    public ResultDto<List<GetKoreanAndMathQuestionRes>> getMathQuestion(GetKoreanAndMathQuestionReq p){
        return ResultDto.<List<GetKoreanAndMathQuestionRes>>builder()
                .build();
    }

    @PostMapping("english")
    public ResultDto<List<GetKoreanAndMathQuestionRes>> getEnglishQuestion(){
        return ResultDto.<List<GetKoreanAndMathQuestionRes>>builder()
                .build();
    }


//    문제에 대한 학년,
//    문제에 대한 과목
//    문제 번호[question_num]
//    답(오답 4개, 정답 1개 - 구분 꼭 안해도됨)[answers]

}
