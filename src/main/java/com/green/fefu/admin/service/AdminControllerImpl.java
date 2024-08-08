package com.green.fefu.admin.service;

import com.green.fefu.admin.model.dto.FindUserListDto;
import com.green.fefu.admin.model.dto.GetUserListDto;
import com.green.fefu.admin.model.req.FindUnAcceptListReq;
import com.green.fefu.admin.model.req.FindUserListReq;
import com.green.fefu.admin.model.req.UpdateUserReq;
import com.green.fefu.admin.model.req.adminUserReq;
import com.green.fefu.admin.test.AdminController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.green.fefu.chcommon.ResponsDataSet.*;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "관리자 승인", description = "관리자 관련 클래스")
public class AdminControllerImpl implements AdminController {
    private final AdminServiceImpl service;

    //    list Get
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")

    @Operation(summary = "회원 가입 시 승인/반려 처리를 위한 유저 List 조회", description = "리턴 => 학부모, 승인 대기 학부모 리스트" +
            "승인 신청일, 자녀 학년, 자녀 학급, 부모 pk, 부모 id, 부모 이름" +
            "<hr/>" +
            "<p> p = > 학부모 교직원 분류 코드 ( 1 -> 학부모 / 2 -> 교직원 ) </p>" +
            "<p> searchWord = > 검색어 입력(이름) ( 필수값 아님! )</p>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "{\n" +
                            "  \"code\": \"학부모\",\n" +
                            "  \"userList\": [\n" +
                            "    {\n" +
                            "      \"createdAt\": \"2024-07-03 15:55:46\",\n" +
                            "      \"grade\": \"1학년\",\n" +
                            "      \"name\": \"부모\",\n" +
                            "      \"pk\": \"1\",\n" +
                            "      \"id\": \"test1234\",\n" +
                            "      \"class\": \"1반\"\n" +
                            "    },\n" +
                            "  ]\n" +
                            "}"
            ),
            @ApiResponse(responseCode = "404",
                    description = "에러 난 이유 설명"
            ),
            @ApiResponse(responseCode = "401",
                    description = "JWT ACCESSTOKEN 에러 ( 토큰을 헤더에 추가해 주세요 )"
            ),
            @ApiResponse(responseCode = "403",
                    description = "해당 유저는 사용 권한이 없음"
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity findUnAcceptList(@ParameterObject @ModelAttribute FindUnAcceptListReq p) {
        log.info("parameter p: {}", p);
        Map map = new HashMap();
        map = service.findUnAcceptList(p, map);
        return new ResponseEntity<>(map, OK);
    }


    //    반려
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    @Operation(summary = "유저 회원가입 반려", description = "리턴 => 없음")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "리턴값 없음!"
            ),
            @ApiResponse(responseCode = "404",
                    description = "에러 난 이유 설명"
            ),
            @ApiResponse(responseCode = "401",
                    description = "JWT ACCESSTOKEN 에러 ( 토큰을 헤더에 추가해 주세요 )"
            ),
            @ApiResponse(responseCode = "403",
                    description = "해당 유저는 사용 권한이 없음"
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity deleteUser(@ParameterObject @ModelAttribute @Valid adminUserReq p) {
        log.info("deleteUser req : {}", p);
        service.deleteUser(p);
        return new ResponseEntity<>(OK);
    }


    //    승인
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    @Operation(summary = "유저 회원가입 승인", description = "리턴 => 없음")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "리던값 없음!"
            ),
            @ApiResponse(responseCode = "404",
                    description = "에러 난 이유 설명"
            ),
            @ApiResponse(responseCode = "401",
                    description = "JWT ACCESSTOKEN 에러 ( 토큰을 헤더에 추가해 주세요 )"
            ),
            @ApiResponse(responseCode = "403",
                    description = "해당 유저는 사용 권한이 없음"
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity acceptUser(@ParameterObject @ModelAttribute @Valid adminUserReq p) {
        log.info("acceptUser req : {}", p);
        service.acceptUser(p);
        return new ResponseEntity<>(OK);
    }

//    검색 기능 ( 학부모, 교직원 )
    @GetMapping(value = "list",produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    @Operation(summary = "교직원 학부모 현황 조회 ( 재직자 / 퇴사자 or 활성화 / 비활성화 )", description = "교직원, 학부모 등의 상태 리스트 불러오는 api" +
            "\"<hr/>\" +\n" +
            "            \"<p> p = > 학부모 교직원 분류 코드 ( 1 -> 학부모 / 2 -> 교직원 ) </p>\" +\n" +
            "            \"<p> searchWord = > 검색어 입력(이름) ( 필수값 아님! )</p>\")" +
            "<p>check = > 퇴사자 포함 체크박스 여부 ( 1 -> 체크 안됨 / 2 -> 체크 됨 )</p>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = ""
            ),
            @ApiResponse(responseCode = "404",
                    description = "에러 난 이유 설명"
            ),
            @ApiResponse(responseCode = "401",
                    description = "JWT ACCESSTOKEN 에러 ( 토큰을 헤더에 추가해 주세요 )"
            ),
            @ApiResponse(responseCode = "403",
                    description = "해당 유저는 사용 권한이 없음"
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity findUserList(@ParameterObject @ModelAttribute @Valid FindUserListReq p) {
        List<FindUserListDto> list = new ArrayList();
        list = service.findUserList(p, list);
        return new ResponseEntity<>(list, OK);
    }
//    자퇴 확인 처리
    @PatchMapping
    public ResponseEntity updateUser(@RequestBody @Valid UpdateUserReq p) {
        service.updateUser(p);
        return new ResponseEntity<>(OK);
    }

}
