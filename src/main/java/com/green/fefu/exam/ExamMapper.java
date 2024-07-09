package com.green.fefu.Exam;

import com.green.fefu.Exam.model.ExamReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExamMapper {
    long examIns(ExamReq p);

}
