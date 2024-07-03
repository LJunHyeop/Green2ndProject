package com.green.fefu.exam;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.green.fefu.chcommon.ResponsDataSet.OK;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/api/exam")
@RequiredArgsConstructor
@Tag(name = "학생 성적 CRUD", description = "성적관련 관련 클래스")
public class ExamControllerImpl {
    private final ExamServiceImpl service;

//    성적 입력
//    성적 수정
//    성적
}
