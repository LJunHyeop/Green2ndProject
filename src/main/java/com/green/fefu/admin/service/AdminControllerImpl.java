package com.green.fefu.admin.service;

import com.green.fefu.admin.model.req.adminUserReq;
import com.green.fefu.admin.test.AdminController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class AdminControllerImpl implements AdminController {
    private final AdminServiceImpl service;

    //    list Get
    @GetMapping("{p}")

    @Operation(summary = "승인 필요한 계정List 불러오기", description = "리턴 => 학부모, 승인 대기 학부모 리스트" +
            "승인 신청일, 자녀 학년, 자녀 학급, 부모 pk, 부모 id, 부모 이름")
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
    public ResponseEntity findUnAcceptList(@PathVariable @Schema(example = "1", description = "1-> 부모List, 2-> 선생List") int p) {
        log.info("parameter p: {}", p);
        Map map = new HashMap();
        log.info("{}", p);
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
    public ResponseEntity acceptUser(@ParameterObject @ModelAttribute adminUserReq p) {
        log.info("acceptUser req : {}", p);
        try {
            service.acceptUser(p);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        return new ResponseEntity<>(OK);
    }

    @GetMapping("access-token")
    @Operation(summary = "엑세스 토큰 재 발행", description = "리턴 => 토큰값")
    public ResponseEntity getRefreshToken(HttpServletRequest req) {
        Map map = new HashMap<>();
        try {
            map = service.getAccessToken(map, req);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        return new ResponseEntity<>(OK);
    }
}
