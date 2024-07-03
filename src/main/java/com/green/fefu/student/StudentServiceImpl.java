package com.green.fefu.student;

import com.green.fefu.chcommon.Parser;
import com.green.fefu.chcommon.PatternCheck;
import com.green.fefu.chcommon.Validation;
import com.green.fefu.common.CustomFileUtils;
import com.green.fefu.security.AuthenticationFacade;
import com.green.fefu.student.model.dto.*;
import com.green.fefu.student.model.req.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.green.fefu.teacher.model.dataset.ExceptionMsgDataSet.BIRTH_TYPE_ERROR;
import static com.green.fefu.student.model.dataset.StudentDBMaxLength.*;
import static com.green.fefu.student.model.dataset.ExceptionMsgDataSet.*;
import static com.green.fefu.student.model.dataset.StudentMapNamingData.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl {
    private final StudentMapper mapper;
    private final Validation validation;
    private final PatternCheck patternCheck;
    private final CustomFileUtils customFileUtils;
    private final AuthenticationFacade authenticationFacade;

    //    학생 데이터 넣기
    @Transactional
    public Map createStudent(createStudentReq p, MultipartFile pic, Map map) throws Exception {
//        파일 관련 프로세스
        fileProcess(p, pic);
//        널 체크
        createStudentNullCheck(p);
//        타입 체크
        createStudentTypeCheck(p);
//        길이 체크
        createStudentLengthCheck(p);

        int result = mapper.createStudent(p);

        if (result != 1) {
            throw new RuntimeException(QUERY_RESULT_ERROR);
        }

        map.put(STUDENT_PK, p.getPk());

        return map;
    }

    private void createStudentNullCheck(createStudentReq p) throws Exception {
        validation.nullCheck(p.getGrade());
        validation.nullCheck(p.getName());
        validation.nullCheck(p.getGender());
        validation.nullCheck(p.getBirth());
        validation.nullCheck(p.getAddr());
        validation.nullCheck(p.getEtc());
        validation.nullCheck(p.getEngName());
        validation.nullCheck(p.getPhone());
    }

    private void createStudentTypeCheck(createStudentReq p) throws Exception {
        patternCheck.nameCheck(p.getName());
        patternCheck.phoneCheck(p.getPhone());
        validation.typeCheck(p.getBirth(), LocalDate.class, BIRTH_TYPE_ERROR);
        patternCheck.gradeCheck(p.getGrade());

    }

    private void createStudentLengthCheck(createStudentReq p) throws Exception {
        validation.lengthCheck(p.getName(), STUDENT_MAX_NAME);
        validation.lengthCheck(p.getGender(), STUDENT_MAX_GENDER);
        if (p.getPic() != null) {
            validation.lengthCheck(p.getPic(), STUDENT_MAX_PIC);
        }
        validation.lengthCheck(p.getAddr(), STUDENT_MAX_ADDR);
        validation.lengthCheck(p.getEtc(), STUDENT_MAX_ETC);
        validation.lengthCheck(p.getPhone(), STUDENT_MAX_PHONE);
        validation.lengthCheck(p.getEngName(), STUDENT_MAX_ENG_NAME);
    }

    private void fileProcess(createStudentReq p, MultipartFile pic) throws Exception {
        if (pic != null) {
            String uuidFileName = customFileUtils.makeRandomFileName(pic);
            p.setPic(uuidFileName);
            try {
                String path = String.format("student/%s", p.getPk());
                customFileUtils.makeFolders(path);
                String target = String.format("%s/%s", path, uuidFileName);
                customFileUtils.transferTo(pic, target);
            } catch (Exception e) {
                throw new RuntimeException(FILE_ERROR);
            }
        }
    }

    //=====================================================================================================================
//   학생 삭제
    public void deleteStudent(deleteStudentReq p) throws Exception {
//        널체크
        deleteStudentNullCheck(p);
//        타입체크
        deleteStudentTypeCheck(p);

        int result = mapper.deleteStudent(p);
        if (result != 1) {
            throw new RuntimeException(QUERY_RESULT_ERROR);
        }
    }

    private void deleteStudentNullCheck(deleteStudentReq p) throws Exception {
        validation.nullCheck(p.getPk().toString());
        validation.nullCheck(p.getState().toString());
    }

    private void deleteStudentTypeCheck(deleteStudentReq p) throws Exception {
        validation.typeCheck(p.getPk().toString(), Long.class, TYPE_ERROR);
        validation.typeCheck(p.getState().toString(), Integer.class, TYPE_ERROR);
        if (p.getState() < 1 || p.getState() > 3) {
            throw new RuntimeException(STATE_VARIATION_ERROR);
        }
    }

    //=====================================================================================================================
//    선생의 담당 학급 학생리스트 불러오기
    public List getStudentList(List<getStudent> list) throws Exception {
        list = mapper.getStudentList(authenticationFacade.getLoginUserId());
        if (list.size() == 0) {
            throw new RuntimeException(QUERY_RESULT_ERROR);
        }
        return list;
    }

    //=====================================================================================================================
    public Map getStudentDetail(long pk, Map map) throws Exception {
        getDetail result = mapper.getStudentDetail(pk);
        map.put(STUDENT_PK, result.getPk());
        map.put(STUDENT_NAME, result.getName());
        map.put(STUDENT_GENDER, result.getGender());
        map.put(STUDENT_BIRTH, result.getBirth());
        map.put(STUDENT_PHONE, result.getPhone());
        map.put(STUDENT_ETC, result.getEtc());
        map.put(STUDENT_CREATED_AT, result.getCreatedAt());
        map.put(STUDENT_PIC, result.getPic());
        map.put(PARENT_ID, result.getParentId());
        map.put(PARENT_NAME, result.getParentName());
        map.put(CONNET, result.getConnet());
        map.put(PARENT_PHONE, result.getParentPhone());
        map.put(TEACHER_NAME, result.getTeacherName());

        String classData = Parser.classParser(result.getUClass());
        String[] addr = Parser.addressParser(result.getAddr());
        map.put(STUDENT_CLASS, classData);
        map.put(STUDENT_ZONE_CODE, addr[0]);
        map.put(STUDENT_ADDR, addr[1]);

//       역대 etc
//       역대 성적 넣어야하지 않나?

        return map;
    }

    //=====================================================================================================================
//    학생 정보 수정
    public void updateStudent(updateStudentReq p) throws Exception {
        if (p.getAddr() != null && p.getZoneCode() != null) {
            p.setFullAddr(Parser.addressParserMerge(p.getZoneCode(), p.getAddr()));
        }
        mapper.updateStudent(p);
    }

    //=====================================================================================================================
//    부모 없는 학생 List 출력
    public List getStudentListForParent(List<getListForNoParent> list) throws Exception {
        list = mapper.getStudentListForParent();
        for (getListForNoParent p : list) {
            p.setGrade(Parser.classParser(p.getGrade()));
            p.setPhone(Parser.phoneParser(p.getPhone()));
        }
        return list;
    }
}