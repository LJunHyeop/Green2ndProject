package com.green.fefu.online;

import com.green.fefu.common.model.ResultDto;
import com.green.fefu.online.model.PostOnlineQuestionEnglishListeningReq;
import com.green.fefu.online.model.PostOnlineQuestionEnglishWordReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/online/english")
public class OnlineEnglishControllerImpl {
    private final OnlineEnglishServiceImpl service;
    @PostMapping
    public ResultDto<Integer> PostEnglishWordQuestion(@RequestPart PostOnlineQuestionEnglishWordReq p, @RequestPart MultipartFile pic){
        int result=1;
        return ResultDto.<Integer>builder()
                .result(result)
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .build();
    }

    @PostMapping("listen")
    public ResultDto<Integer> PostEnglishListeningQuestion(@RequestPart PostOnlineQuestionEnglishListeningReq p, @RequestPart MultipartFile pic){
        int result=1;
        return ResultDto.<Integer>builder()
                .result(result)
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .build();
    }
}
