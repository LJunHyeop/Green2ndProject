package com.green.fefu.admin.service;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@ActiveProfiles("tdd")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AdminMapperTest {

    @Autowired
    private AdminMapper adminMapper;

    @Test
    void getParentList() {
    }

    @Test
    void getTeacherList() {
    }

    @Test
    void delParent() {
    }

    @Test
    void delTeacher() {
    }

    @Test
    void updParent() {
    }

    @Test
    void updTeacher() {
    }
}