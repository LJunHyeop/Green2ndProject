package com.green.fefu.online;

import com.green.fefu.common.model.ResultDto;
import com.green.fefu.online.model.GetEnglishListeningQuestionRes;
import com.green.fefu.online.model.GetEnglishWordsQuestionReq;
import com.green.fefu.online.model.PostOnlineQuestionEnglishListeningReq;
import com.green.fefu.online.model.PostOnlineQuestionEnglishWordReq;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/online/english")
@Tag(name = "온라인 학습-영어", description = "영어 단어, 듣기 문제 CRUD")
@Slf4j
public class OnlineEnglishControllerImpl {
    private final OnlineEnglishServiceImpl service;

    @PostMapping("/words")
    public ResultDto<Integer> postEnglishWordQuestion(@RequestPart PostOnlineQuestionEnglishWordReq p, @RequestPart MultipartFile pic){
        log.info("controller - p {}", p);
        log.info("controller - pic {}", pic);
        int result=service.postEnglishWordQuestion(p,pic);
        return ResultDto.<Integer>builder()
                .result(result)
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .build();
    }

    @PostMapping("/listening")
    public ResultDto<Integer> postEnglishListeningQuestion(@RequestPart PostOnlineQuestionEnglishListeningReq p, @RequestPart MultipartFile pic){
        log.info("controller - p {}", p);
        log.info("controller - pic {}", pic);
        int result=service.PostEnglishListeningQuestion(p,pic);
        return ResultDto.<Integer>builder()
                .result(result)
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .build();
    }

    @GetMapping
    public ResultDto<List<GetEnglishListeningQuestionRes>> getEnglishWords(@ModelAttribute GetEnglishWordsQuestionReq p){
        log.info("controller - p {}", p);
        List<GetEnglishListeningQuestionRes> list=service.getEnglishWords(p);
        return ResultDto.<List<GetEnglishListeningQuestionRes>>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .result(list)
                .build();

    }
}
