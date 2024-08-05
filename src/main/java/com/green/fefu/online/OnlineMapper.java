package com.green.fefu.online;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OnlineMapper {
    Long teacherClass(Long teaId);
}
