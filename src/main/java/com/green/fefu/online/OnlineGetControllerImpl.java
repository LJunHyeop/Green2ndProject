package com.green.fefu.online;

import com.green.fefu.common.model.ResultDto;
import com.green.fefu.online.model.GetKoreanAndMathQuestionReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class OnlineGetControllerImpl {
    @PostMapping("korean")
    public ResultDto<Integer> getKoreanQuestion(GetKoreanAndMathQuestionReq p){
        return ResultDto.<Integer>builder()
                .build();
    }
    @PostMapping("math")
    public ResultDto<Integer> getMathQuestion(GetKoreanAndMathQuestionReq p){
        return ResultDto.<Integer>builder()
                .build();
    }

    @PostMapping("english")
    public ResultDto<Integer> getEnglishQuestion(){
        return ResultDto.<Integer>builder()
                .build();
    }


//    문제에 대한 학년,
//    문제에 대한 과목
//    문제 번호[question_num]
//    문제 내용[question]
//    이미지(필수 x)[question_img]
//    답(오답 4개, 정답 1개 - 구분 꼭 안해도됨)[answers]

}
