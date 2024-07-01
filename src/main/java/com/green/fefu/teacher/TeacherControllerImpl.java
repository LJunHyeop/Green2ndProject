package com.green.fefu.teacher;


import com.green.fefu.teacher.model.req.*;
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
public class TeacherControllerImpl {
    private final TeacherServiceImpl service;

    //    선생님 회원가입
    @PostMapping("/sign-up")
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
    public ResponseEntity FindTeacherPassword(@ParameterObject @ModelAttribute FindTeacherPasswordReq p) {
        log.info("FindTeacherPassword req: {}", p);
        try {
            service.FindTeacherPassword(p);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        return new ResponseEntity<>(OK);
    }

    //    선생님 비밀번호 변경
    @PutMapping
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
    public ResponseEntity TeacherDetail() {
        Map map = new HashMap();
        try {
            service.TeacherDetail(map);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        return new ResponseEntity<>(OK);
    }

//    선생님 정보 변경

//    ADMIN 회원가입 승인 ( ADMIN Package 로 옮길 예정 )

//    선생님 탈퇴 ( 어드민 만 가능 ) ==> 개발 가능성 Low
}
