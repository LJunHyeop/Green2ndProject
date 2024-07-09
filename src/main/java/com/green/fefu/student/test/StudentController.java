package com.green.fefu.student.test;

import com.green.fefu.student.model.req.createStudentReq;
import com.green.fefu.student.model.req.deleteStudentReq;
import com.green.fefu.student.model.req.updateStudentReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public interface StudentController {
//    학생 생성
    ResponseEntity createStudent(createStudentReq p, MultipartFile pic);
//    학생 상태 변경
    ResponseEntity deleteStudent(@RequestParam deleteStudentReq p);
//    선생기준 담당 학급 리스트
    ResponseEntity getStudentList();
//    학생 한명의 데이터
    ResponseEntity getStudentDetail(@RequestParam long pk);
//    학생 업데이트
    ResponseEntity updateStudent(@RequestBody updateStudentReq p);
//    부모가 없는 학생들 리스트
    ResponseEntity getStudentListForParent();
}