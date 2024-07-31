package com.green.fefu.online;

import com.green.fefu.common.model.ResultDto;
import com.green.fefu.online.model.GetKoreanQuestionReq;
import com.green.fefu.online.model.PostOnlineQuestionReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/online")
public class OnlineKoreanController {
    @PostMapping
    public ResultDto<Integer> PostKoreanQuestion(PostOnlineQuestionReq p){
        int result=1;
        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .result(result)
                .resultMsg(HttpStatus.OK.toString())
                .build();
    }

    @GetMapping
    public ResultDto<List<GetKoreanQuestionReq>> GetKoreanQuestion(){
        List<GetKoreanQuestionReq> result=new ArrayList<>();
        return ResultDto.<List<GetKoreanQuestionReq>>builder()
                .result(result)
                .build();
    }

}
