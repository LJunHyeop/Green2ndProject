package com.green.fefu.teacher;

import com.green.fefu.teacher.model.req.CreateTeacherReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeacherMapper {
    int CreateTeacher(CreateTeacherReq p);
}
