package com.green.fefu.online;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OnlineMapper {
    long teacherClass(Long teaId);
    long parentsClass(Long parentsId, Long stuId);
    long studentClass(Long stuId);

}
