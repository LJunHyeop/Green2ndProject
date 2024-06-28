package com.green.fefu.teacher;


import com.green.fefu.teacher.model.req.CreateTeacherReq;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

import static com.green.fefu.chcommon.ResponsDataSet.*;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
@Tag(name = "선생님 CRUD", description = "선생님 관련 클래스")
public class TeacherControllerImpl {
    private final TeacherServiceImpl service;

    //    선생님 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity CreateTeacher(@RequestBody CreateTeacherReq p) {
        Map map;
        try {
            map = service.CreateTeacher(p);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),NOT_FOUND);
        }

        return new ResponseEntity<>(map, OK);
    }

//    선생님 로그인

//    선생님 중복확인 ( 아이디 )

//    선생님 아이디 찾기

//    선생님 비밀번호 찾기

//    선생님 비밀번호 변경

//    선생님 내정보 불러오기
}
