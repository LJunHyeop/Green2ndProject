package com.green.fefu.student.service;

import com.green.fefu.student.model.dto.getUserTest;
import com.green.fefu.student.model.req.createStudentReq;
import com.green.fefu.student.model.req.deleteStudentReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentMapperTest {
    @Autowired
    private StudentMapper mapper;

    private final String msg = "원하는 값이 아닙니다.";
    private final String resultMsg = "값이 변경되지 않음";

    @Test
    @DisplayName("학생 생성")
    void createStudent() {
        createStudentReq p = new createStudentReq();
        p.setPic("a.jpg");
        p.setAddr("1234 # 서울 판교");
        p.setPhone("010-0000-0000");
        p.setGrade("10101");
        p.setBirth("2024-07-09");
        p.setName("홍길동");
        p.setEngName("hong");
        p.setEtc("갑각류 알러지 있음");
        p.setGender("남");
        int result = mapper.createStudent(p);
        assertEquals(1, result, resultMsg);
        getUserTest entity = mapper.selOneTest(p.getPk());
        assertEquals(p.getPic(), entity.getPic(), msg);
        assertEquals(p.getAddr(), entity.getAddr(), msg);
        assertEquals(p.getPhone(), entity.getPhone(), msg);
        assertEquals(p.getGrade(), entity.getGrade(), msg);
        assertEquals(p.getBirth(), entity.getBirth(), msg);
        assertEquals(p.getName(), entity.getName(), msg);
        assertEquals(p.getEngName(), entity.getEngName(), msg);
        assertEquals(p.getEtc(), entity.getEtc(), msg);
        assertEquals(p.getGender(), entity.getGender(), msg);
    }

    @Test
    @DisplayName("학생 삭제")
    void deleteStudent() {
        deleteStudentReq p = new deleteStudentReq();
        p.setPk(1L);
        p.setState(2);
        int result = mapper.deleteStudent(p);
        assertEquals(1, result, resultMsg);
        getUserTest entity = mapper.selOneTest(p.getPk());
        assertEquals(p.getState().toString(), entity.getState(), msg);

        deleteStudentReq p2 = new deleteStudentReq();
        p2.setPk(2L);
    }

    @Test
    @DisplayName("선생의 담당 학급 학생 리스트")
    void getStudentList() {
    }

    @Test
    @DisplayName("학생 상세 정보")
    void getStudentDetail() {
    }

    @Test
    @DisplayName("학생 정보 변경")
    void updateStudent() {
    }

    @Test
    @DisplayName("부모 없는 학생 리스트")
    void getStudentListForParent() {
    }

    @Test
    @DisplayName("학생 학년 변경")
    void updStudentGrade() {
    }

    @Test
    @DisplayName("새 학급에 학생 추가")
    void insNewClass() {
    }
}