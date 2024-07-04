package com.green.fefu.teacher.service;


import com.green.fefu.teacher.model.req.*;
import com.green.fefu.teacher.test.TeacherController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
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
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
@Tag(name = "선생님 CRUD", description = "선생님 관련 클래스")
public class TeacherControllerImpl implements TeacherController {
    private final TeacherServiceImpl service;

    //    선생님 회원가입
    @PostMapping("/sign-up")
    @Operation(summary = "선생님 회원가입", description = "리턴 => 선생님 PK값")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "teacherPk : \"1\""
            ),
            @ApiResponse(responseCode = "404",
                    description = "에러 난 이유 설명"
            ),
    })
    @Override
    public ResponseEntity CreateTeacher(@RequestBody CreateTeacherReq p) {
        log.info("CreateTeacher req: {}", p);
        Map map = new HashMap();
        try {
            map = service.CreateTeacher(p, map);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }

        return new ResponseEntity<>(map, OK);
    }

    //    선생님 로그인
    @PostMapping("/sign-in")
    @Operation(summary = "선생님 로그인", description = "리턴 => 이름, 이메일, 담당학급, 엑세스토큰")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description =
                            "<p> name : \"홍길동\"</p>" +
                                    "<p> email : \"test1234@naver.com\"</p>" +
                                    "<p> class : \"1학년 1반\"</p>" +
                                    "<p> accessToken : \"asdasdqwdzxc\"</p>"
            ),
            @ApiResponse(responseCode = "404",
                    description = "에러 난 이유 설명"
            )
    })
    @Override
    public ResponseEntity LogInTeacher(@RequestBody LogInTeacherReq p, HttpServletResponse res) {
        log.info("LogInTeacher req: {}", p);
        Map map = new HashMap();

        try {
            map = service.LogInTeacher(p, map, res);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }

        return new ResponseEntity<>(map, OK);
    }

    //    선생님 중복확인 ( 아이디, 이메일 )
    @GetMapping("duplicate")
    @Operation(summary = "선생님 아이디 or 이메일 중복확인", description = "리턴 => 없음 <br><strong>아이디 이메일 둘중 하나만 넣어주세요</strong>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description =
                            "리턴값 없음!"
            ),
            @ApiResponse(responseCode = "404",
                    description = "에러 난 이유 설명"
            )
    })
    @Override
    public ResponseEntity CheckDuplicate(@ParameterObject @ModelAttribute CheckDuplicateReq p) {
        log.info("CheckDuplicate req: {}", p);
        try {
            service.CheckDuplicate(p);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        return new ResponseEntity<>(OK);
    }


    //    선생님 아이디 찾기
    @GetMapping("find_id")
    @Operation(summary = "선생님 아이디 찾기", description = "리턴 => 선생님 ID값")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description =
                            "id : \"test1234\""
            ),
            @ApiResponse(responseCode = "404",
                    description = "에러 난 이유 설명"
            )
    })
    @Override
    public ResponseEntity FindTeacherId(@ParameterObject @ModelAttribute FindTeacherIdReq p) {
        log.info("FindTeacherId req: {}", p);
        Map map = new HashMap();
        try {
            service.FindTeacherId(p, map);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        return new ResponseEntity<>(map, OK);
    }

    //    선생님 비밀번호 찾기
    @GetMapping("find_pwd")
    @Operation(summary = "선생님 비밀번호 찾기 ( 문자 발송 )", description = "리턴 => 랜덤 코드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description =
                            "randomCode : \"111111\""
            ),
            @ApiResponse(responseCode = "404",
                    description = "에러 난 이유 설명"
            )
    })
    @Override
    public ResponseEntity FindTeacherPassword(@ParameterObject @ModelAttribute FindTeacherPasswordReq p) {
        log.info("FindTeacherPassword req: {}", p);
        Map map = new HashMap();
        try {
            service.FindTeacherPassword(p, map);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        return new ResponseEntity<>(map, OK);
    }

    //    선생님 비밀번호 변경
    @PutMapping("put_pwd")
    @Operation(summary = "선생님 비밀번호 변경", description = "리턴 => 없음")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description =
                            "리턴값 없음!"
            ),
            @ApiResponse(responseCode = "404",
                    description = "에러 난 이유 설명"
            )
    })
    @Override
    public ResponseEntity ChangePassWord(@RequestBody ChangePassWordReq p) {
        log.info("ChangePassWord req: {}", p);
        try {
            service.ChangePassWord(p);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        return new ResponseEntity<>(OK);
    }

    //    선생님 내정보 불러오기
    @GetMapping
    @Operation(summary = "선생님 내정보 불러오기", description = "리턴 => 아이디, 이름, 전화번호, 이메일, 성별, 담당학급, 생년월일")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description =
                            "<p> id : \"test1234\"</p>" +
                                    "<p> name : \"홍길동\"</p>" +
                                    "<p> phone : \"010-0000-0000\"</p>" +
                                    "<p> email : \"test1234@naver.com\"</p>" +
                                    "<p> gender : \"여자\"</p>" +
                                    "<p> class : \"1학년 1반\"</p>" +
                                    "<p> birth : \"1980-01-01\"</p>"
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
    @Override
    public ResponseEntity TeacherDetail() {
        Map map = new HashMap();
        try {
            service.TeacherDetail(map);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        return new ResponseEntity<>(map, OK);
    }

    //    선생님 정보 변경
    @PatchMapping
    @Operation(summary = "선생님 정보 변경", description = "리턴 => 없음")
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
    @Override
    public ResponseEntity ChangeTeacher(@RequestBody ChangeTeacherReq p) {
        log.info("ChangeTeacher req: {}", p);
        try {
            service.ChangeTeacher(p);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        return new ResponseEntity<>(OK);
    }
}
