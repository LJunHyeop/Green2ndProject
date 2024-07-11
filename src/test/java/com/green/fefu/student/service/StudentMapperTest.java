package com.green.fefu.student.service;

import com.green.fefu.student.model.dto.getDetail;
import com.green.fefu.student.model.dto.getStudent;
import com.green.fefu.student.model.dto.getUserTest;
import com.green.fefu.student.model.req.createStudentReq;
import com.green.fefu.student.model.req.deleteStudentReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.ArrayList;
import java.util.List;

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
        p2.setState(3);
        result = mapper.deleteStudent(p2);
        assertEquals(1, result, resultMsg);
        getUserTest entity2 = mapper.selOneTest(p2.getPk());
        assertEquals(p2.getState().toString(), entity2.getState(), msg);
    }

    @Test
    @DisplayName("선생의 담당 학급 학생 리스트")
    void getStudentList() {
        List<getStudent> list = new ArrayList<>();
        getStudent p1 = new getStudent();
        p1.setName("유학생");
        p1.setGender("여");
        p1.setBirth("2009-01-01");
        p1.setPhone("010-9999-0000");
        p1.setParentName("유부모");
        p1.setParentPhone("010-1234-3456");
        getStudent p2 = new getStudent();
        p2.setName("노학생");
        p2.setGender("남");
        p2.setBirth("2008-12-12");
        p2.setPhone("010-0000-1111");
        p2.setParentName("노부모");
        p2.setParentPhone("010-2345-4567");

        list.add(p2);
        list.add(p1);

        List<getStudent> result = mapper.getStudentList(15L);
        assertEquals(2, result.size(), resultMsg);
        assertEquals(result.size(), list.size(), resultMsg);
        for (int i = 0; i < list.size(); i++) {
            assertEquals(result.get(i).getName(), list.get(i).getName(), msg);
            assertEquals(result.get(i).getGender(), list.get(i).getGender(), msg);
            assertEquals(result.get(i).getBirth(), list.get(i).getBirth(), msg);
            assertEquals(result.get(i).getPhone(), list.get(i).getPhone(), msg);
            assertEquals(result.get(i).getParentName(), list.get(i).getParentName(), msg);
            assertEquals(result.get(i).getParentPhone(), list.get(i).getParentPhone(), msg);
        }
    }

    @Test
    @DisplayName("학생 상세 정보")
    void getStudentDetail() {
        getDetail p = new getDetail();
        p.setPk("1");
        p.setName("홍길동");
        p.setBirth("2010-05-15");
        p.setGender("남");
        p.setPhone("010-0000-0000");
        p.setAddr("1234 # 서울 판교로 112");
        p.setEtc("갑각류 알러지 있음");
        p.setCreatedAt("2024-07-04 08:02:22");
        p.setParentId("parent1");
        p.setUClass("201");
        p.setParentName("김부모");
        p.setConnet("부");
        p.setParentPhone("010-1234-5678");
        p.setPic(null);
        p.setTeacherName("정선생");
        getDetail result = mapper.getStudentDetail(1L);
        assertEquals(p,result);
        assertNotNull(result);

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