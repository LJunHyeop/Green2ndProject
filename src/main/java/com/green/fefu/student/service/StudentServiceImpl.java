package com.green.fefu.student.service;

import com.green.fefu.chcommon.Parser;
import com.green.fefu.chcommon.PatternCheck;
import com.green.fefu.chcommon.Validation;
import com.green.fefu.common.CustomFileUtils;
import com.green.fefu.security.AuthenticationFacade;
import com.green.fefu.student.model.dto.*;
import com.green.fefu.student.model.req.*;
import com.green.fefu.student.test.StudentService;
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
public class StudentServiceImpl implements StudentService {
    private final StudentMapper mapper;
    private final Validation validation;
    private final PatternCheck patternCheck;
    private final CustomFileUtils customFileUtils;
    private final AuthenticationFacade authenticationFacade;

    //    학생 데이터 넣기
    @Transactional
    @Override
    public Map createStudent(createStudentReq p, MultipartFile pic, Map map) throws Exception {
//        널 체크
        createStudentNullCheck(p);
//        타입 체크
        createStudentTypeCheck(p);
//        길이 체크
        createStudentLengthCheck(p);

        fileNameChange(p, pic);

        int result = mapper.createStudent(p);
//        파일 관련 프로세스
        fileProcess(p, pic);

        if (result != 1) {
            throw new RuntimeException(QUERY_RESULT_ERROR);
        }

//        grade에서 반 정보 뽑아내기 ( 지금은 10101 << 이 폼임 )
        String classData = p.getGrade().substring(0, 3);
        ClassInsert classInsert = new ClassInsert();
        classInsert.setClassPk(Integer.parseInt(classData));
        classInsert.setTeacherPk(p.getTeacherPk());
        classInsert.setStudentPk(p.getPk());
//        반이 없으면 반 생성
        mapper.insertClassIfNotExists(classInsert);
//        반에 학생 추가
        mapper.insertClassStudent(classInsert);


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
            try {
                String path = String.format("student/%s", p.getPk());
                customFileUtils.makeFolders(path);
                String target = String.format("%s/%s", path, p.getPic());
                customFileUtils.transferTo(pic, target);
            } catch (Exception e) {
                throw new RuntimeException(FILE_ERROR);
            }
        }
    }

    private void fileNameChange(createStudentReq p, MultipartFile pic) throws Exception {
        if (pic != null) {
            String uuidFileName = customFileUtils.makeRandomFileName(pic);
            p.setPic(uuidFileName);
        }
    }

    //=====================================================================================================================
//   학생 삭제
    @Override
    @Transactional
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
    @Override
    public List getStudentList(List<getStudent> list) throws Exception {
        list = mapper.getStudentList(authenticationFacade.getLoginUserId());
        if (list.size() == 0) {
            throw new RuntimeException(QUERY_RESULT_ERROR);
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setNum(i + 1);
        }
        return list;
    }

    //=====================================================================================================================
    @Override
    public Map getStudentDetail(long pk, Map map) throws Exception {
        getDetail result = mapper.getStudentDetail(pk);
        if (result == null) {
            throw new RuntimeException(QUERY_RESULT_ERROR);
        }
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
        String classData = null;
        if (result.getUClass() != null) {
            classData = Parser.classParser(result.getUClass());
        }
        String[] addr = {null, null};
        if (result.getAddr() != null) {
            addr = Parser.addressParser(result.getAddr());
        }
        log.info("data : {}", classData);
        map.put(STUDENT_CLASS, classData);
        map.put(STUDENT_ZONE_CODE, addr[0]);
        map.put(STUDENT_ADDR, addr[1]);
        map.put(STUDENT_DETAIL, addr[2]);

//       역대 etc
//        이건 구현 해야함
//        리스트 젤 마지막꺼 자르고 줘야함 ( 마지막껀 현재 정보기 때문 )
        List<prevStudentEtc> prevEtc = mapper.selPrevEtc(pk);
        log.info("prevEtc.size() = " + prevEtc.size());
        prevEtc.remove(prevEtc.size() - 1);
        log.info("removePrevEtc.size() = " + prevEtc.size());
        for (int i = 0; i < prevEtc.size(); i++) {
            String etcClass = Parser.classParser(prevEtc.get(i).getUClass());
            prevEtc.get(i).setUClass(etcClass);
        }
        map.put(PREV_ETC_LIST, prevEtc);

        return map;
    }

    //=====================================================================================================================
//    학생 정보 수정
    @Override
    @Transactional
    public void updateStudent(updateStudentReq p) throws Exception {
        updateStudentDataCheck(p);
        int result = mapper.updateStudent(p);
        if (result != 1) {
            throw new RuntimeException();
        }
    }

    private void updateStudentDataCheck(updateStudentReq p) throws Exception {
        if (p.getAddr() != null && p.getZoneCode() != null) {
            p.setFullAddr(Parser.addressParserMerge(p.getZoneCode(), p.getAddr(), p.getDetail()));
        }
        if (p.getPk() < 1) {
            throw new RuntimeException(REQUIRED_DATA_ERROR);
        }
        if (p.getPhone() != null) {
            patternCheck.phoneCheck(p.getPhone());
        }
        if (p.getName() != null) {
            patternCheck.nameCheck(p.getName());
        }
    }

    //=====================================================================================================================
//    부모 없는 학생 List 출력
    @Override
    public List<getListForNoParent> getStudentListForParent(List<getListForNoParent> list, String searchWord) throws Exception {
        list = mapper.getStudentListForParent(searchWord);
        log.info("data : {}", list);
        for (getListForNoParent p : list) {
            p.setGrade(Parser.classParser(p.getGrade()));
        }
        log.info("data2 : {}", list);
        return list;
    }


    @Transactional
    public void studentAdvanceGrade(List<studentAdvanceGradeReq> p) throws Exception {
//        널체크
        if (p == null || p.isEmpty()) {
            throw new RuntimeException(REQUIRED_DATA_ERROR);
        }
        for (studentAdvanceGradeReq item : p) {
            studentAdvanceGradeNullCheck(item);
//        타입 체크
            studentAdvanceGradeTypeCheck(item);
//        데이터 파싱
            studentAdvanceGradeParse(item);
        }

//        쿼리 실행
        for (studentAdvanceGradeReq item : p) {
            String etc = mapper.getStudentEtc(Long.parseLong(item.getStudentPk()));
            item.setEtc(etc);
            mapper.updStudentEtc(Long.parseLong(item.getStudentPk()), etc);
            int result1 = mapper.updStudentGrade(item);
            int result2 = mapper.insNewClass(item);
//        쿼리 에러
            if (result1 != 1) {
                throw new RuntimeException(QUERY_RESULT_ERROR);
            }
            if (result2 != 1) {
                throw new RuntimeException(QUERY_RESULT_ERROR);
            }
        }
    }

    private void studentAdvanceGradeNullCheck(studentAdvanceGradeReq p) throws Exception {
        if (p.getStudentPk() != null && p.getGrade() != null) {
            return;
        } else {
            throw new RuntimeException(REQUIRED_DATA_ERROR);
        }
    }

    private void studentAdvanceGradeTypeCheck(studentAdvanceGradeReq p) throws Exception {
        validation.typeCheck(p.getStudentPk(), Long.class, TYPE_ERROR);
        validation.typeCheck(p.getGrade(), Integer.class, TYPE_ERROR);
        patternCheck.gradeNumberCheck(p.getGrade());
    }

    private void studentAdvanceGradeParse(studentAdvanceGradeReq p) throws Exception {
//        20101 -> 201
        p.setSubNumber(p.getGrade().substring(0, 3));
    }
}