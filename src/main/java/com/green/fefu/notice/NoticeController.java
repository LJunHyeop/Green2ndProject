package com.green.fefu.notice;

import com.green.fefu.common.model.ResultDto;
import com.green.fefu.notice.model.*;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.SSLEngineResult;
import java.beans.ConstructorProperties;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/notice")
public class NoticeController {
    private final NoticeService service;

    @PostMapping("")
    public ResultDto<Integer> postNotice(@RequestBody PostNoticeReq p){
        int result=service.postNotice(p);
        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("성공적으로 업로드 되었습니다.")
                .result(result)
                .build();
    }

    @GetMapping("")
    public ResultDto<List<GetNoticeRes>> getNotice(@ModelAttribute @ParameterObject GetNoticeReq p){
        List<GetNoticeRes> list=service.getNotice(p);
        return ResultDto.<List<GetNoticeRes>>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("성공적으로 조회되었습니다.")
                .result(list)
                .build();
    }

    @PutMapping("")
    public ResultDto<Integer> putNotice(@RequestBody PutNoticeReq p){
        int result=service.putNotice(p);
        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("성공적으로 수정되었습니다.")
                .result(result)
                .build();
    }

    @DeleteMapping("")
    public ResultDto<Integer> deleteNotice(@ModelAttribute @ParameterObject DeleteNoticeReq p){
        int result=service.deleteNotice(p);
        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("성공적으로 삭제했습니다.")
                .result(result)
                .build();
    }
}
