package com.green.fefu.admin;

import com.green.fefu.admin.model.req.adminUserReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.green.fefu.chcommon.ResponsDataSet.*;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "관리자 승인", description = "관리자 관련 클래스")
public class AdminControllerImpl {
    private final AdminServiceImpl service;

    //    list Get
    @GetMapping("{p}")
    @Operation(summary = "승인 필요한 계정List 불러오기", description = "리턴 => 학부모")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description =
                            "<p> code : \"학부모 / 교직원\"</p>" +
                            "<p> userList : [" +
                                    "<p>&emsp; pk : \"1\"</p>" +
                                    "<p>&emsp; id : \"test1234\"</p>" +
                                    "<p>&emsp; name : \"홍길동\"</p>" +
                                    "<p>&emsp; grade : \"1학년\"</p>" +
                                    "<p>&emsp; class : \"1반\"</p>" +
                                    "<p>&emsp; createdAt : \"2024-07-01 16:03:13\"</p>" +
                                    "]</p>"
            ),
            @ApiResponse(responseCode = "404",
                    description = "에러 난 이유 설명")
    })
    public ResponseEntity findUnAcceptList(@PathVariable @Schema(example = "1", description = "1-> 부모List, 2-> 선생List") int p) {
        log.info("parameter p: {}", p);
        Map map = new HashMap();
        log.info("{}",p);
        try {
            map = service.findUnAcceptList(p, map);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        return new ResponseEntity<>(map, OK);
    }


    //    반려
    @DeleteMapping
    @Operation(summary = "유저 회원가입 반려", description = "리턴 => 없음")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "리턴값 없음!"
            ),
            @ApiResponse(responseCode = "404",
                    description = "에러 난 이유 설명")
    })
    public ResponseEntity deleteUser(@ParameterObject @ModelAttribute adminUserReq p) {
        log.info("deleteUser req : {}", p);
        try {
            service.deleteUser(p);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        return new ResponseEntity<>(OK);
    }


    //    승인
    @PutMapping
    @Operation(summary = "유저 회원가입 승인", description = "리턴 => 없음")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "리던값 없음!"
            ),
            @ApiResponse(responseCode = "404",
                    description = "에러 난 이유 설명")
    })
    public ResponseEntity acceptUser(@ParameterObject @ModelAttribute adminUserReq p) {
        log.info("acceptUser req : {}", p);
        try {
            service.acceptUser(p);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        return new ResponseEntity<>(OK);
    }


}
