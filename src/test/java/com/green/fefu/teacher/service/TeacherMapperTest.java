package com.green.fefu.teacher.service;

import com.green.fefu.teacher.model.dto.EntityArgument;
import com.green.fefu.teacher.model.dto.TeacherEntity;
import com.green.fefu.teacher.model.req.CreateTeacherReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeacherMapperTest {
    @Autowired
    TeacherMapper mapper;

    @Test
    @DisplayName("선생 회원가입")
    void createTeacher() {
        CreateTeacherReq p = new CreateTeacherReq();
        p.setFullAddr("12345 # 서울 판교 1234");
        p.setName("홍길동");
        p.setPassword("$2a$10$QkkFHwq6USBUkLPr/W4LL.qNSx8WakVOahlLVNqQ3cuXqitKcZDvS");
        p.setTeacherId("test1234");
        p.setPhone("010-0000-0000");
        p.setEmail("test1234@naver.com");
        p.setGender("남");
        p.setBirth("2024-06-28");
        int result = mapper.CreateTeacher(p);
        assertEquals(1, result, "회원가입 실패");
    }

    @Test
    void getTeacher() {
        TeacherEntity entity = mapper.GetTeacher(EntityArgument.builder()
                .pk(1L)
                .build()
        );
        assertNotNull(entity, "데이터가 들어오지 않음");
        assertEquals("teacher1", entity.getId(), "아이디가 다름");
        assertEquals("password1", entity.getPassword(), "비밀번호 다름");
        assertEquals("지선생", entity.getName(), "이름 다름");
        assertEquals("010-3333-4444", entity.getPhone(), "전화번호 다름");
        assertEquals("teacher1@school.com", entity.getEmail(), "이메일 다름");
        assertEquals("여", entity.getGender(), "성별 다름");
        assertEquals("1980-03-10", entity.getBirth(), "생년월일 다름");
        assertEquals("ROLE_TEAHCER", entity.getAuth(), "권한 다름");
        assertEquals("서울시 서초구", entity.getAddr(), "주소 다름");
        assertEquals("2", entity.getAcept(), "승인여부 다름");
        assertEquals("2024-07-04 08:02:22", entity.getCreatedAt(), "생성일 다름");
        assertEquals("2024-07-09 09:48:09", entity.getUpdatedAt(), "수정일 다름");

        TeacherEntity entity1 = mapper.GetTeacher(EntityArgument.builder()
                .id("teacher2")
                .build()
        );
        assertNotNull(entity1, "데이터가 들어오지 않음");
        assertEquals(2L, entity1.getPk(), "pk값 다름");
        assertEquals("password2", entity1.getPassword(), "비밀번호 다름");
        assertEquals("최선생", entity1.getName(), "이름 다름");
        assertEquals("010-4444-5555", entity1.getPhone(), "전화번호 다름");
        assertEquals("teacher2@school.com", entity1.getEmail(), "이메일 다름");
        assertEquals("남", entity1.getGender(), "성별 다름");
        assertEquals("1985-06-15", entity1.getBirth(), "생년월일 다름");
        assertEquals("ROLE_TEAHCER", entity1.getAuth(), "권한 다름");
        assertEquals("경기도 성남시", entity1.getAddr(), "주소 다름");
        assertEquals("2", entity1.getAcept(), "승인여부 다름");
        assertEquals("2024-07-04 08:02:22", entity1.getCreatedAt(), "생성일 다름");
        assertEquals("2024-07-09 09:47:15", entity1.getUpdatedAt(), "수정일 다름");
    }

    @Test
    void getCurrentClassesByTeacherId() {
        String result = mapper.getCurrentClassesByTeacherId(1L);
        assertEquals("101", result ,"담당 학급이 다릅니다");
        result = mapper.getCurrentClassesByTeacherId(10L);
        assertEquals("502", result ,"담당 학급이 다릅니다");
    }

    @Test
    void changePassWord() {
    }

    @Test
    void changeTeacher() {
    }
}