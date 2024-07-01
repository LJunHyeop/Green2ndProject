package com.green.fefu.admin;

import com.green.fefu.admin.model.dto.GetUserListDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<GetUserListDto> getParentList();
    List<GetUserListDto> getTeacherList();
}
