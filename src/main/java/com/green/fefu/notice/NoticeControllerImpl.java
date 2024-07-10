package com.green.fefu.notice;

import com.green.fefu.common.model.ResultDto;
import com.green.fefu.notice.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/notice")
@Tag(name="알림장 Notice",description="알림장 CRUD")
public class NoticeControllerImpl implements NoticeController{
    private final NoticeService service;


    @PostMapping("")
    @Operation(summary = "알림장 작성", description = "<strong>각 학급의 알림장</strong>")
    public ResultDto<Integer> postNotice(@RequestBody PostNoticeReq p){
        int result = service.postNotice(p);
        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("성공적으로 업로드 되었습니다.")
                .result(result)
                .build();
    }

    @GetMapping("")
    @Operation(summary = "알림장 조회", description = "<strong>반 별 알림장 조회</strong>")
    public ResultDto<List<GetNoticeRes>> getNotice(@ModelAttribute @ParameterObject GetNoticeReq p){
        List<GetNoticeRes> list=service.getNotice(p);
        return ResultDto.<List<GetNoticeRes>>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("성공적으로 조회되었습니다.")
                .result(list)
                .build();
    }

    @PutMapping("") //구현 예정
    @Operation(summary = "알림장 수정", description = "<strong>알림장 수정</strong>")
    public ResultDto<Integer> putNotice(@RequestBody PutNoticeReq p){
        int result=service.putNotice(p);
        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("성공적으로 수정되었습니다.")
                .result(result)
                .build();
    }

    @DeleteMapping("")
    @Operation(summary = "알림장 삭제", description = "<strong>필요없는 알림장 삭제</strong>")
    public ResultDto<Integer> deleteNotice(@ModelAttribute @ParameterObject DeleteNoticeReq p){
        int result=service.deleteNotice(p);
        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("성공적으로 삭제했습니다.")
                .result(result)
                .build();
    }
}
