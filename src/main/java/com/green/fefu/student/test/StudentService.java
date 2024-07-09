package com.green.fefu.student.test;

import com.green.fefu.student.model.dto.getListForNoParent;
import com.green.fefu.student.model.dto.getStudent;
import com.green.fefu.student.model.req.createStudentReq;
import com.green.fefu.student.model.req.deleteStudentReq;
import com.green.fefu.student.model.req.updateStudentReq;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface StudentService {
    Map createStudent(createStudentReq p, MultipartFile pic, Map map) throws Exception;
    void deleteStudent(deleteStudentReq p) throws Exception;
    List getStudentList(List<getStudent> list) throws Exception;
    Map getStudentDetail(long pk, Map map) throws Exception;
    void updateStudent(updateStudentReq p) throws Exception;
    List getStudentListForParent(List<getListForNoParent> list) throws Exception;
}
