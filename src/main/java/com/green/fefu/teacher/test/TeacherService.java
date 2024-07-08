package com.green.fefu.teacher.test;

import com.green.fefu.teacher.model.req.*;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

public interface TeacherService {
    Map CreateTeacher(CreateTeacherReq p, Map map) throws Exception;

    Map LogInTeacher(LogInTeacherReq p, Map map, HttpServletResponse res) throws Exception;

    void CheckDuplicate(CheckDuplicateReq p) throws Exception;

    Map FindTeacherId(FindTeacherIdReq p, Map map) throws Exception;

    void FindTeacherPassword(FindTeacherPasswordReq p, Map map) throws Exception;

    void ChangePassWord(ChangePassWordReq p) throws Exception;

    Map TeacherDetail(Map map) throws Exception;

    void ChangeTeacher(ChangeTeacherReq p) throws Exception;
}
