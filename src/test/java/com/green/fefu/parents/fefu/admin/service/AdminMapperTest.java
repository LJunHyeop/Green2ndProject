package com.green.fefu.parents.fefu.admin.service;

import com.green.fefu.admin.service.AdminMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

@MybatisTest
@ActiveProfiles("tdd")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AdminMapperTest {

    @Autowired
    private AdminMapper mapper;

    @Test
    @DisplayName("학부모 회원가입 승인 대기 리스트")
    void getParentList() {

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