package com.green.fefu.admin.service;

import com.green.fefu.admin.model.dto.GetUserListDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@ActiveProfiles("tdd")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AdminMapperTest {

    @Autowired
    private AdminMapper mapper;

    @Test
    @DisplayName("학부모 회원가입 승인 대기 리스트")
    void getParentList() {
        GetUserListDto user1 = new GetUserListDto();
        List<GetUserListDto> list = new ArrayList<>();
        user1.setPk("1");
        user1.setId("test");
        user1.setName("길동");
        user1.setGrade("10101");
        user1.setCreatedAt("2024-07-04 17:02:12");
        list.add(user1);
        List<GetUserListDto> result = mapper.getParentList();
        assertEquals(list.size(), result.size(),"결과값의 갯수가 다릅니다.");
        assertEquals(list.get(0), result.get(0), "결과 값이 다릅니다.");
    }

    @Test
    @DisplayName("교직원 회원가입 승인 대기 리스트")
    void getTeacherList() {
    }

    @Test
    @DisplayName("학부모 회원가입 반려")
    void delParent() {
    }

    @Test
    @DisplayName("교직원 회원가입 반려")
    void delTeacher() {
    }

    @Test
    @DisplayName("학부모 회원가입 승인")
    void updParent() {
    }

    @Test
    @DisplayName("교직원 회원가입 승인")
    void updTeacher() {
    }
}