package com.green.fefu.student;

import com.green.fefu.student.model.dto.getListForNoParent;
import com.green.fefu.student.model.dto.getStudent;
import com.green.fefu.student.model.req.createStudentReq;
import com.green.fefu.student.model.req.deleteStudentReq;
import com.green.fefu.student.model.req.updateStudentReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.green.fefu.chcommon.ResponsDataSet.*;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/api/student")
@RequiredArgsConstructor
@Tag(name = "학생 CRUD", description = "학생 관련 클래스")
public class StudentControllerImpl {
    private final StudentServiceImpl service;

    //    학생 생성
    @PostMapping
    @Operation(summary = "학생 생성", description = "리턴 => 학생 PK")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "studentPk : \"1\""
            ),
            @ApiResponse(responseCode = "404",
                    description = "에러 난 이유 설명"
            ),
    })
    public ResponseEntity createStudent(@RequestPart createStudentReq p, @RequestParam MultipartFile pic) {
        log.info("createStudent req : {}", p);
        Map map = new HashMap();
        try {
            map = service.createStudent(p, pic, map);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }

        return new ResponseEntity<>(map, OK);
    }

    //    학생 삭제??? ( 데이터 존재 but, 삭제 X ) -> 전학, 졸업 등등
    @DeleteMapping
    @Operation(summary = "학생 삭제", description = "리턴 => 없음")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "리턴값 없음!"
            ),
            @ApiResponse(responseCode = "404",
                    description = "에러 난 이유 설명"
            ),
    })
    public ResponseEntity deleteStudent(@RequestParam deleteStudentReq p) {
        log.info("deleteStudent req : {}", p);
        try {
            service.deleteStudent(p);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        return new ResponseEntity<>(OK);
    }

    //    선생기준 -> 자기 반 학생 리스트 들고오기
    @GetMapping
    @Operation(summary = "담당 학급의 학생 List", description = "리턴 => ")
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
    public ResponseEntity getStudentList() {
        List<getStudent> result = new ArrayList<>();
        try {
            result = service.getStudentList(result);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        return new ResponseEntity<>(result, OK);
    }

    //    선생기준 -> 자기 반 학생 한명 전체 데이터 들고오기
    @GetMapping("detail")
    @Operation(summary = "학생 한명의 정보 가져오기", description = "리턴 => 생년월일, 관계, 선생이름," +
            " 생성일, 주소, 우편번호, 사진, 이름, 전화번호, 성별, 학년 학급, 기타사항, pk, 부모 아이디, 부모 이름, 부모 전화번호")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "{\n" +
                            "  \"studentBirth\": \"2024-07-03\",\n" +
                            "  \"connet\": \"부\",\n" +
                            "  \"teacherName\": \"백창현\",\n" +
                            "  \"studentAddr\": \"대구 중구\",\n" +
                            "  \"parentPhone\": \"010-1111-1111\",\n" +
                            "  \"studentClass\": \"1학년 1반\",\n" +
                            "  \"studentEtc\": null,\n" +
                            "  \"studentPk\": \"1\",\n" +
                            "  \"studentId\": \"test1234\",\n" +
                            "  \"studentZoneCode\": \"1324\",\n" +
                            "  \"parentName\": \"부모\",\n" +
                            "  \"studentGender\": \"남자\",\n" +
                            "  \"studentName\": \"홍길동\",\n" +
                            "  \"studentPic\": null,\n" +
                            "  \"studentPhone\": \"010-0000-0000\",\n" +
                            "  \"studentCreatedAt\": \"2024-07-03 15:55:03\"\n" +
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
    public ResponseEntity getStudentDetail(@RequestParam long pk) {
        log.info("getStudentDetail req : {}", pk);
        Map map = new HashMap();
        try {
            map = service.getStudentDetail(pk, map);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        return new ResponseEntity<>(map, OK);
    }

    //    선생기준 -> 학생 정보 수정
    @PutMapping
    @Operation(summary = "학생 한명의 정보 수정", description = "리턴 => 없음")
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
    public ResponseEntity updateStudent(@RequestBody updateStudentReq p) {
        log.info("updateStudent req : {}", p);
        try {
            service.updateStudent(p);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        return new ResponseEntity<>(OK);
    }


    //    부모 회원가입시 -> 이름 기준 검색 -> 학생 LIST 불러오기 ( 이름 + 전화번호 + 사진 + 학년 + 반 )
    @GetMapping("list")
    @Operation(summary = "부모가 회원가입 하지 않은 학생 List", description = "리턴 => 사진, 학생 pk, 이름, 학년 반 번호, 전화번호 ( 끝 4자리 )")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "[\n" +
                            "  {\n" +
                            "    \"pic\": null,\n" +
                            "    \"pk\": 1,\n" +
                            "    \"name\": \"홍길동\",\n" +
                            "    \"grade\": \"1학년 1반 1번\",\n" +
                            "    \"phone\": \"0000\"\n" +
                            "  }\n" +
                            "]"
            ),
            @ApiResponse(responseCode = "404",
                    description = "에러 난 이유 설명"
            )
    })
    public ResponseEntity getStudentListForParent() {
        List<getListForNoParent> result = new ArrayList<>();
        try {
            result = service.getStudentListForParent(result);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        return new ResponseEntity<>(result, OK);
    }
}
