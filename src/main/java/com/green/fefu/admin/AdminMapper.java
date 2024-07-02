package com.green.fefu.admin;

import com.green.fefu.admin.model.dto.GetUserListDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<GetUserListDto> getParentList();
    List<GetUserListDto> getTeacherList();
    int delParent(long pk);
    int delTeacher(long pk);
    int updParent(long pk);
    int updTeacher(long pk);
}
