package com.green.fefu.online;

import com.green.fefu.common.model.ResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/online")
public class OnlineKoreanController {
    @PostMapping
    public ResultDto<Integer> PostKoreanQuestion(){
        int result=1;
        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .result(result)
                .resultMsg(HttpStatus.OK.toString())
                .build();
    }
}
