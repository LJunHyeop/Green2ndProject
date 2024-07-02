package com.green.fefu.student;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity createStudent(){

        return new ResponseEntity<>(OK);
    }

//    학생 삭제??? ( 데이터 존재 but, 삭제 X ) -> 전학, 졸업 등등
    @DeleteMapping
    public ResponseEntity deleteStudent(){

        return new ResponseEntity<>(OK);
    }

//    선생기준 -> 자기 반 학생 리스트 들고오기
    @GetMapping
    public ResponseEntity getStudentList(){

        return new ResponseEntity<>(OK);
    }
//    선생기준 -> 자기 반 학생 한명 전체 데이터 들고오기
    @GetMapping("detail")
    public ResponseEntity getStudentDetail(){

        return new ResponseEntity<>(OK);
    }

//    선생기준 -> 학생 정보 수정
    @PutMapping
    public ResponseEntity updateStudent(){

        return new ResponseEntity<>(OK);
    }

//    선생기준 -> 학생 성적 입력
    @PatchMapping
    public ResponseEntity updateStudentScore(){

        return new ResponseEntity<>(OK);
    }

//    부모 회원가입시 -> 이름 기준 검색 -> 학생 LIST 불러오기 ( 이름 + 전화번호 + 사진 + 학년 + 반 )
    @GetMapping("list")
    public ResponseEntity getStudentListForParent(){

        return new ResponseEntity<>(OK);
    }
}
